package com.example.oauth2.exceptions;

public class NoAuthenticatedUserException extends RuntimeException {
    public NoAuthenticatedUserException(final String message) {
        super(message);
    }

    public static NoAuthenticatedUserException with(final String message) {
        return new NoAuthenticatedUserException(message);
    }
}
