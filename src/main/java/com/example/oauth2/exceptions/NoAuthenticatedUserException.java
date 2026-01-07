package com.example.oauth2.exceptions;

public class NoAuthenticatedUserException extends RuntimeException {
    public NoAuthenticatedUserException(String message) {
        super(message);
    }

    public static NoAuthenticatedUserException with(String message) {
        return new NoAuthenticatedUserException(message);
    }
}
