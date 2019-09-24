package com.asapp.backend.challenge.service;

import com.asapp.backend.challenge.resources.MessageResource;

import java.util.List;

public interface MessageService {

    MessageResource save(MessageResource message);

    List<MessageResource> getMessages(Integer userLoggedId, Integer recipient, Integer start, Integer limit);

}
