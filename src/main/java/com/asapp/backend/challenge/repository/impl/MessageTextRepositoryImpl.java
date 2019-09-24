package com.asapp.backend.challenge.repository.impl;

import com.asapp.backend.challenge.repository.MessageContentRepository;
import com.asapp.backend.challenge.repository.config.GenericDao;
import com.asapp.backend.challenge.repository.model.Message;
import com.asapp.backend.challenge.repository.model.MessageText;
import com.asapp.backend.challenge.resources.MessageContentResource;
import com.asapp.backend.challenge.resources.MessageContentResourceType;
import com.asapp.backend.challenge.resources.MessageResource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageTextRepositoryImpl extends GenericDao<MessageText, Integer> implements MessageContentRepository {

    @Override
    public Boolean support(MessageContentResourceType type) {
        return MessageContentResourceType.TEXT.equals(type);
    }

    @Override
    public void save(Message message, MessageContentResource messageContent) {
        MessageText messageText = fromMessageResource(message, messageContent);
        super.save(messageText);
    }

    @Override
    public List<MessageResource> listMessages(List<Integer> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return listFromMessagesIds(ids, MessageText.class)
                .map(value -> toMessageResource(value))
                .collect(Collectors.toList());
    }

    private MessageText fromMessageResource(Message message, MessageContentResource messageContent) {
        return MessageText.builder()
                .message(message)
                .text(messageContent.getText())
                .build();
    }

    private MessageResource toMessageResource(MessageText value) {
        MessageContentResource content =  MessageContentResource.builder()
                .text(value.getText())
                .build();

        return toMessageResource(value.getMessage(), content);
    }
}
