package com.example.oauth2.exceptions;

public class CsrfTokenRequiredException extends RuntimeException {
    public CsrfTokenRequiredException(final String message) {
        super(message);
    }

    public static CsrfTokenRequiredException with(final String message) {
        return new CsrfTokenRequiredException(message);
    }
}
