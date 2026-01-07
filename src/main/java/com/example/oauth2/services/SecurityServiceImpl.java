package com.example.oauth2.services;

import com.example.oauth2.abstractions.SecurityService;
import com.example.oauth2.exceptions.NoAuthenticatedUserException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String getUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw NoAuthenticatedUserException.with("No authenticated user");
        }

        final Jwt jwt = (Jwt) authentication.getPrincipal();

        return jwt.getSubject();
    }
}
