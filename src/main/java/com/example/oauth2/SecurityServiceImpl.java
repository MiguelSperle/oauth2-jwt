package com.example.oauth2;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String getCurrentUserId() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw NoAuthenticatedUserException.with("No authenticated user");
        }

        final var jwt = (Jwt) authentication.getPrincipal();

        return jwt.getSubject();
    }
}
