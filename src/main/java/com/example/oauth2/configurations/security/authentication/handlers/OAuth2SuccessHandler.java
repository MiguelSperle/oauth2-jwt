package com.example.oauth2.configurations.security.authentication.handlers;

import com.example.oauth2.abstractions.JwtService;
import com.example.oauth2.configurations.security.authentication.CustomOAuth2User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        final String jwt = this.jwtService.generateToken(customOAuth2User.getUserId(), "USER");

        final Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        cookie.setAttribute("SameSite", "Strict");

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:3000/home");
    }
}

