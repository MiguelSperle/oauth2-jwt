package com.example.oauth2.utils;

import java.util.List;

public record ApiError(
        List<String> errors,
        String errorType
) {
    public static ApiError from(List<String> errors, String errorType) {
        return new ApiError(errors, errorType);
    }

    public int tt() {
        return 0;
    }
}
