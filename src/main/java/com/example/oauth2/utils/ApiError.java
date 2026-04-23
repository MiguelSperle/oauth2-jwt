package com.example.oauth2.utils;

import java.time.Instant;
import java.util.List;

public record ApiError(String message, int status, List<Error> errors, Instant timestamp) {
    public record Error(String property, String message) {
    }

    public static ApiError from(final String message, final int status, final List<Error> errors, final Instant timestamp) {
        return new ApiError(message, status, errors, timestamp);
    }

    public static ApiError from(final String message, final int status, final Instant timestamp) {
        return new ApiError(message, status, List.of(), timestamp);
    }
}
