package com.asapp.backend.challenge;

import com.asapp.backend.challenge.controller.AuthController;
import com.asapp.backend.challenge.controller.HealthController;
import com.asapp.backend.challenge.controller.MessagesController;
import com.asapp.backend.challenge.controller.UsersController;
import com.asapp.backend.challenge.filter.TokenValidatorFilter;
import com.asapp.backend.challenge.repository.config.HibernateUtil;
import com.asapp.backend.challenge.utils.Path;
import spark.Spark;

public class Application {

    public static void main(String[] args) {

        // Spark Configuration
        Spark.port(8080);

        // Configure Endpoints
        // Users
        Spark.post(Path.USERS, UsersController.createUser);
        // Auth
        Spark.post(Path.LOGIN, AuthController.login);
        // Messages
        Spark.before(Path.MESSAGES, TokenValidatorFilter.validateUser);
        Spark.post(Path.MESSAGES, MessagesController.sendMessage);
        Spark.get(Path.MESSAGES, MessagesController.getMessages);
        // Health
        Spark.post(Path.HEALTH, HealthController.check);

        // Load objects in memory
        HibernateUtil.getSessionFactory().openSession();
    }

}
