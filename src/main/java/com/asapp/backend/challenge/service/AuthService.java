package com.asapp.backend.challenge.service;

import com.asapp.backend.challenge.repository.model.Token;
import com.asapp.backend.challenge.repository.model.User;

public interface AuthService {

    Token createToken(User user);

    Token getToken(User user);

    Token getToken(String token);

    Boolean isValid(String token);
}
