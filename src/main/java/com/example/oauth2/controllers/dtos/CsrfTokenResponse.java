package com.example.oauth2.controllers.dtos;

public record CsrfTokenResponse(String csrfToken, String headerName) {
    public static CsrfTokenResponse from(String csrfToken, String headerName) {
        return new CsrfTokenResponse(csrfToken, headerName);
    }
}
