package com.example.oauth2.utils;

import java.time.Instant;
import java.util.List;

public record ApiError(String message, List<String> errors, Instant timestamp) {
    public static ApiError from(final String message, final List<String> errors, final Instant timestamp) {
        return new ApiError(message, errors, timestamp);
    }

    public static ApiError from(final String message, final Instant timestamp) {
        return new ApiError(message, List.of(), timestamp);
    }
}
