package com.example.oauth2;

public interface JwtService {
    String generateToken(String userId, String role);
}
