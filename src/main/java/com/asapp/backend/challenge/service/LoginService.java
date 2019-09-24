package com.asapp.backend.challenge.service;

import com.asapp.backend.challenge.resources.LoginResponseResource;

public interface LoginService {

    LoginResponseResource createUser(String username, String password);

    LoginResponseResource loginUser(String username, String password);

    Integer getUserId(String s);
}
