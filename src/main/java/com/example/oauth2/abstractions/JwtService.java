package com.example.oauth2.abstractions;

public interface JwtService {
    String generateToken(String userId, String role);
}
