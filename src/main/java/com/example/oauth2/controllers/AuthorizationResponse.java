package com.example.oauth2.controllers;

public record AuthorizationResponse(
        String accessToken
) {
    public static AuthorizationResponse from(String accessToken) {
        return new AuthorizationResponse(accessToken);
    }
}
