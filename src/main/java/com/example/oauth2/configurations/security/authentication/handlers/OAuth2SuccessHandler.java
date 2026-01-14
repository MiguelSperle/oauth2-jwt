package com.example.oauth2.configurations.security.authentication.handlers;

import com.example.oauth2.abstractions.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println(oAuth2User.getAttributes()); // data of google user

        // * Here you can search or create a user in the database

        final String userId = UUID.randomUUID().toString();

        final String accessToken = this.jwtService.generateAccessToken(userId, "USER", Collections.emptyList());
        final String refreshToken = UUID.randomUUID().toString();

        final ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None") // backend domain is different of frontend domain that's why is None
                .path("/api/auth/refresh")
                .maxAge(Duration.ofDays(15))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        final String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth/callback")
                .queryParam("accessToken", accessToken).build().toUriString();

        response.sendRedirect(redirectURL);
    }
}

