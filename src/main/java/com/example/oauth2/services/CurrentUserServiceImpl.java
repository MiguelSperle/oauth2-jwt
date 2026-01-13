package com.example.oauth2.services;

import com.example.oauth2.abstractions.CurrentUserService;
import com.example.oauth2.exceptions.NoAuthenticatedUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public String getUserId() {
        final Jwt jwt = this.getJwt();
        return jwt.getSubject();
    }

    private Jwt getJwt() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            throw NoAuthenticatedUserException.with("No authenticated user");
        }

        return jwtAuthenticationToken.getToken();
    }
}