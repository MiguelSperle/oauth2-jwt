package com.example.oauth2.controllers.dtos;

public record AuthorizationResponse(
        String accessToken
) {
    public static AuthorizationResponse from(final String accessToken) {
        return new AuthorizationResponse(accessToken);
    }
}
