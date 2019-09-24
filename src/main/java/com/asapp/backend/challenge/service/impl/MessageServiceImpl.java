package com.asapp.backend.challenge.service.impl;

import com.asapp.backend.challenge.repository.MessageContentRepository;
import com.asapp.backend.challenge.repository.MessageRepository;
import com.asapp.backend.challenge.repository.UserRepository;
import com.asapp.backend.challenge.repository.model.Message;
import com.asapp.backend.challenge.repository.model.User;
import com.asapp.backend.challenge.resources.MessageResource;
import com.asapp.backend.challenge.service.MessageService;
import com.google.inject.Inject;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {
    @Inject
    private MessageRepository messageRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private Set<MessageContentRepository> messageContentRepositories;

    @Override
    public MessageResource save(MessageResource message) {
        Date now = Date.from(Instant.now());
        // Save message
        Message messageIn = Message.builder()
                .recipient(userRepository.get(message.getRecipient()))
                .sender(userRepository.get(message.getSender()))
                .timestamp(now)
                .type(message.getContent().getType())
                .build();
        Integer messageId = messageRepository.save(messageIn);
        messageIn.setId(messageId);

        // Save message content
        messageContentRepositories.stream()
                .filter(m -> m.support(message.getContent().getType()))
                .forEach(m -> m.save(messageIn, message.getContent()));

        message.setId(messageId);
        message.setTimestamp(now);
        return message;
    }

    @Override
    public List<MessageResource> getMessages(Integer userLoggedId, Integer recipientId, Integer start, Integer limit) {
        User sender = userRepository.get(userLoggedId);
        User recipient = userRepository.get(recipientId);

        // Get all messages ids to show
        List<Integer> ids = messageRepository.listMessages(sender, recipient, start, limit)
                .stream().map(Message::getId).collect(Collectors.toList());

        // Get content from those ids and sort by id
        List<MessageResource> total = messageContentRepositories.stream()
                .map(m -> m.listMessages(ids))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(MessageResource::getId))
                .collect(Collectors.toList());

        return total;
    }
}
