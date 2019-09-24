package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.BasicModule;
import com.asapp.backend.challenge.resources.ErrorResource;
import com.asapp.backend.challenge.resources.MessageResource;
import com.asapp.backend.challenge.service.LoginService;
import com.asapp.backend.challenge.service.MessageService;
import com.asapp.backend.challenge.service.exception.UserNotFoundException;
import com.asapp.backend.challenge.utils.JSONUtil;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class MessagesController {
    private static LoginService loginService;
    private static MessageService messageService;

    public static Route sendMessage = (Request req, Response rep) -> {
        MessageResource message = new Gson().fromJson(req.body(), MessageResource.class);
        rep.type("application/json");

        // Validate message payload
        if (message==null || message.getSender() == null || message.getRecipient() == null
                || message.getContent() == null || message.getContent().getType() == null) {
            return ErrorResource.getError(rep, 400, "Sender, Recipient and Type are required");
        }

        // Validate content payload
        if (!validateContent(message)) {
            return ErrorResource.getError(rep, 400, "Content is invalid");
        }

        loadService();
        // User must be logged to send a message
        String auth = req.headers("Authorization");
        Integer userLoggedId = null;
        if (auth != null) {
            // Remove "bearer" prefix from header if exists
            String[] in = auth.split(" ");
            try {
                userLoggedId = loginService.getUserId(in[in.length - 1]);
            } catch (UserNotFoundException nf) {
                return ErrorResource.getError(rep, 404, "Invalid token - No user found");
            }
        }

        if (userLoggedId == null || userLoggedId != message.getSender()) {
            return ErrorResource.getError(rep, 403, "Cannot send message on behalf of another user");
        }

        try {
            MessageResource response = messageService.save(message);
            return JSONUtil.dataToJson(MessageResource.builder()
            .id(response.getId())
            .timestamp(response.getTimestamp())
            .build());
        } catch (UserNotFoundException nf) {
            return ErrorResource.getError(rep, 404, "Recipient not found");
        }
    };

    private static Boolean validateContent(MessageResource message) {
        Boolean validContent = true;
        switch (message.getContent().getType()) {
            case TEXT: validContent = message.getContent().getText() != null;
            break;
            case IMAGE: validContent = message.getContent().getHeight() != null &&
                    message.getContent().getWidth() != null &&
                    message.getContent().getUrl() != null;
            break;
            case VIDEO: validContent = message.getContent().getUrl() != null &&
                    message.getContent().getSource() != null;
        }
        return validContent;
    }

    public static Route getMessages = (Request req, Response rep) -> {
        rep.type("application/json");

        Integer recipient = req.queryMap().get("recipient").integerValue();
        Integer start = req.queryMap().get("start").integerValue();
        Integer limit = req.queryMap().get("limit").integerValue();
        limit = limit == null ? 100 : limit; // Set default value

        // Validate payload
        if (recipient == null || start == null) {
            return ErrorResource.getError(rep, 400, "Recipient and start query params are required");
        }

        loadService();
        // User must be logged to read a message
        String auth = req.headers("Authorization");
        Integer userLoggedId = null;
        if (auth != null) {
            // Remove "bearer" prefix from header if exists
            String[] in = auth.split(" ");
            try {
                userLoggedId = loginService.getUserId(in[in.length - 1]);
            } catch (UserNotFoundException nf) {
                return ErrorResource.getError(rep, 404, "Invalid token - No user found");
            }
        }

        try {
            List<MessageResource> response = messageService.getMessages(userLoggedId, recipient, start, limit);
            return JSONUtil.dataToJson(response);
        } catch (UserNotFoundException nf) {
            return ErrorResource.getError(rep, 404, "Recipient not found");
        }
    };

    // I need this because I'm calling from static method (it's not performant)
    private static void loadService() {
        if (loginService == null || messageService == null) {
            Injector injector = Guice.createInjector(new BasicModule());
            if (loginService == null) { loginService = injector.getInstance(LoginService.class); }
            if (messageService == null) { messageService = injector.getInstance(MessageService.class); }
        }
    }
}
