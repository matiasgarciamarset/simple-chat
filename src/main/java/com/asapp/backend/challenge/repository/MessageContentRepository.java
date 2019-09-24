package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.repository.model.Message;
import com.asapp.backend.challenge.resources.MessageContentResource;
import com.asapp.backend.challenge.resources.MessageContentResourceType;
import com.asapp.backend.challenge.resources.MessageResource;

import java.util.List;

public interface MessageContentRepository {
    Boolean support(MessageContentResourceType type);
    void save(Message message, MessageContentResource messageContent);
    List<MessageResource> listMessages(List<Integer> ids);

    default MessageResource toMessageResource(Message message, MessageContentResource content) {
        content.setType(message.getType());
        return MessageResource.builder()
                .id(message.getId())
                .sender(message.getSender().getId())
                .recipient(message.getRecipient().getId())
                .timestamp(message.getTimestamp())
                .content(content)
                .build();
    }
}
