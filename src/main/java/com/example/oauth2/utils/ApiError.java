package com.example.oauth2.utils;

import java.time.Instant;
import java.util.List;

public record ApiError(
        List<String> errors,
        String errorType,
        Instant timestamp
) {
    public static ApiError from(final List<String> errors, final String errorType, final Instant timestamp) {
        return new ApiError(errors, errorType, timestamp);
    }
}
