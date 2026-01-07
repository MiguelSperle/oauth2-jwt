package com.example.oauth2.configurations.security.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CookieBearerTokenResolver implements BearerTokenResolver {
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/login"
    );
    private static final String ACCESS_TOKEN_COOKIE_NAME = "accessToken";

    @Override
    public String resolve(HttpServletRequest request) {
        final String path = request.getRequestURI();

        if (PUBLIC_PATHS.contains(path)) {
            return null;
        }

        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName());
                if (ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
