package com.asapp.backend.challenge.repository;

import com.asapp.backend.challenge.repository.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsernameAndAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Integer save(User user);
    User get(Integer id);
}
