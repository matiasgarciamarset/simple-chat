package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.BasicModule;
import com.asapp.backend.challenge.resources.ErrorResource;
import com.asapp.backend.challenge.resources.UserResource;
import com.asapp.backend.challenge.service.LoginService;
import com.asapp.backend.challenge.service.exception.UserNotFoundException;
import com.asapp.backend.challenge.utils.JSONUtil;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import spark.*;

public class AuthController {

    public static Route login = (Request req, Response resp) -> {
        UserResource user = new Gson().fromJson(req.body(), UserResource.class);
        resp.type("application/json");

        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return ErrorResource.getError(resp, 400, "Username and password are required");
        }

        try {
            return JSONUtil.dataToJson(getService().loginUser(user.getUsername(), user.getPassword()));
        } catch (UserNotFoundException ue) {
            return ErrorResource.getError(resp, 404, "User or password incorrect");
        } catch (Exception e) {
            resp.status(500);
            return null;
        }
    };

    private static LoginService getService() {
        Injector injector = Guice.createInjector(new BasicModule());
        return injector.getInstance(LoginService.class);
    }
}
