package com.asapp.backend.challenge.repository.impl;

import com.asapp.backend.challenge.repository.MessageContentRepository;
import com.asapp.backend.challenge.repository.config.GenericDao;
import com.asapp.backend.challenge.repository.model.Message;
import com.asapp.backend.challenge.repository.model.MessageImage;
import com.asapp.backend.challenge.resources.MessageContentResource;
import com.asapp.backend.challenge.resources.MessageContentResourceType;
import com.asapp.backend.challenge.resources.MessageResource;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageImageRepositoryImpl extends GenericDao<MessageImage, Integer> implements MessageContentRepository {

    @Override
    public Boolean support(MessageContentResourceType type) {
        return MessageContentResourceType.IMAGE.equals(type);
    }

    @Override
    public void save(Message message, MessageContentResource messageContent) {
        MessageImage messageImage = fromMessageResource(message, messageContent);
        super.save(messageImage);
    }

    @Override
    public List<MessageResource> listMessages(List<Integer> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return listFromMessagesIds(ids, MessageImage.class)
                .map(this::toMessageResource)
                .collect(Collectors.toList());
    }

    private MessageImage fromMessageResource(Message message, MessageContentResource messageContent) {
        return MessageImage.builder()
                .message(message)
                .height(messageContent.getHeight())
                .url(messageContent.getUrl())
                .width(messageContent.getWidth())
                .build();
    }

    private MessageResource toMessageResource(MessageImage value) {
        MessageContentResource content =  MessageContentResource.builder()
                .url(value.getUrl())
                .height(value.getHeight())
                .width(value.getWidth())
                .build();

        return toMessageResource(value.getMessage(), content);
    }
}
