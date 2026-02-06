package com.example.oauth2.exceptions;

public class CsrfTokenRequiredException extends RuntimeException {
    public CsrfTokenRequiredException(String message) {
        super(message);
    }

    public static CsrfTokenRequiredException with(String message) {
        return new CsrfTokenRequiredException(message);
    }
}
