package com.example.oauth2.exceptions;

public class InvalidCsrfTokenException extends RuntimeException {
    public InvalidCsrfTokenException(final String message) {
        super(message);
    }
}
