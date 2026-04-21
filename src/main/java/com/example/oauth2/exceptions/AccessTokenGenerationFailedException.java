package com.example.oauth2.exceptions;

public class AccessTokenGenerationFailedException extends RuntimeException {
    public AccessTokenGenerationFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
