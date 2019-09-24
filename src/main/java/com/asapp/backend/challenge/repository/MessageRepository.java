package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.repository.model.Message;
import com.asapp.backend.challenge.repository.model.User;

import java.util.List;

public interface MessageRepository {
    Integer save(Message message);
    List<Message> listMessages(User sender, User recipient, Integer start, Integer limit);
}
