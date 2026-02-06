package com.example.oauth2.exceptions;

public class InvalidCsrfTokenException extends RuntimeException {
    public InvalidCsrfTokenException(String message) {
        super(message);
    }

    public static InvalidCsrfTokenException with(String message) {
        return new InvalidCsrfTokenException(message);
    }
}
