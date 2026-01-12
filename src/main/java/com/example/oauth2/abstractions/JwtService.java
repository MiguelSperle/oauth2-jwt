package com.example.oauth2.abstractions;

import java.util.List;

public interface JwtService {
    String generateAccessToken(String userId, String role, List<String> permissions);
}
