package com.example.oauth2.services;

import com.example.oauth2.abstractions.SecurityService;
import com.example.oauth2.exceptions.NoAuthenticatedUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String getUserId() {
        final Jwt jwt = this.getJwt();
        return jwt.getSubject();
    }

    private Jwt getJwt() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            throw NoAuthenticatedUserException.with("No authenticated user");
        }

        return jwt;
    }
}
