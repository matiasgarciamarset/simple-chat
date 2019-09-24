package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.repository.model.Token;
import com.asapp.backend.challenge.repository.model.User;

import java.util.Optional;

public interface TokenRepository {
    Optional<Token> findByUser(User user);
    String save(Token token);
    Boolean existsById(String token);
    Optional<Token> findById(String token);
}
