package com.asapp.backend.challenge.filter;

import com.asapp.backend.challenge.BasicModule;
import com.asapp.backend.challenge.service.AuthService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

public class TokenValidatorFilter {

    private static AuthService authService;

    public static Filter validateUser = (Request req, Response resp) -> {
        loadService();
        String auth = req.headers("Authorization");

        boolean isTokenValid = false;
        if (auth != null) {
            // Remove "bearer" prefix from header if exists
            String[] in = auth.split(" ");
            isTokenValid = authService.isValid(in[in.length-1]);
        }

        if (!isTokenValid) {
            Spark.halt(401, "Not valid token");
        }
    };

    // I need this because I'm calling from static method (it's not performant)
    private static void loadService() {
        if (authService == null) {
            Injector injector = Guice.createInjector(new BasicModule());
            authService = injector.getInstance(AuthService.class);
        }
    }
}
