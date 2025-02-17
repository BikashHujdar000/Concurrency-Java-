package com.example.bikash.SpringTest.Exceptions;

import org.apache.tomcat.websocket.AuthenticationException;

public class JwtValidationException extends RuntimeException{

    private String message;

    public JwtValidationException(String message) {
        super(String.format("JWT validation failed: %s", message));
        this.message = message;
    }

}
