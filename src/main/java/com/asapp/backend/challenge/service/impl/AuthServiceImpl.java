package com.asapp.backend.challenge.service.impl;

import com.asapp.backend.challenge.repository.TokenRepository;
import com.asapp.backend.challenge.repository.model.Token;
import com.asapp.backend.challenge.repository.model.User;
import com.asapp.backend.challenge.service.AuthService;
import com.asapp.backend.challenge.service.exception.UserNotFoundException;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class AuthServiceImpl implements AuthService {

    @Inject
    private TokenRepository repository;

    @Override
    public Token createToken(User user) {
        Token newToken = Token.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .build();
        repository.save(newToken);
        return newToken;
    }

    @Override
    public Token getToken(User user) {
        return repository.findByUser(user)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Token getToken(String token) {
        return repository.findById(token)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Boolean isValid(String token) {
        return repository.existsById(token);
    }
}
