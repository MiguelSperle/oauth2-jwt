package com.example.oauth2.utils;

import java.time.Instant;

public final class InstantUtils {
    private InstantUtils() {
    }

    public static Instant now() {
        return Instant.now();
    }

    // Instant is UTC, and with that, it ignores local time zone (it is a universal watch).
}
