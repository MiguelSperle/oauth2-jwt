package com.example.oauth2.utils;

import java.time.Instant;
import java.util.List;

public record ApiError(String message, int statusCode, List<Error> errors, Instant timestamp) {
    public record Error(String property, String message) {
    }

    public static ApiError from(final String message, final int statusCode, final List<Error> errors, final Instant timestamp) {
        return new ApiError(message, statusCode, errors, timestamp);
    }

    public static ApiError from(final String message, final int statusCode, final Instant timestamp) {
        return new ApiError(message, statusCode, List.of(), timestamp);
    }
}
