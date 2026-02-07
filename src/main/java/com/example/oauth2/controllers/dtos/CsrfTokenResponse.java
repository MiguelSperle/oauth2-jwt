package com.example.oauth2.controllers.dtos;

public record CsrfTokenResponse(String csrfToken) {
    public static CsrfTokenResponse from(String csrfToken) {
        return new CsrfTokenResponse(csrfToken);
    }
}
