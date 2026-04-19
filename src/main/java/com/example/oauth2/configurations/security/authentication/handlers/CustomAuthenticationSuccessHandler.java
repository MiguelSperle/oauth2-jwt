package com.example.oauth2.configurations.security.authentication.handlers;

import com.example.oauth2.abstractions.services.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenService jwtTokenService;

    public CustomAuthenticationSuccessHandler(final JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final Authentication authentication
    ) throws IOException {
        final OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println(oAuth2User.getName()); // User identification at the provider(Google, GitHub...)
        System.out.println(oAuth2User.getAttributes()); // Map containing all user attributes in the provider(Google, GitHub...)

        // * Here we will search or create a user in the (database)

        final String userId = UUID.randomUUID().toString(); // * Here will be user id where we are going to retrieve from the (database)

        final String accessToken = this.jwtTokenService.generateAccessToken(userId, "USER", Collections.emptyList());

        final String refreshToken = UUID.randomUUID().toString(); // * Here we are going to call a (usecase) that will create the refresh token

        final ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("none") // backend domain is different of frontend domain that's why is "none"
                .path("/auth/refresh")
                .maxAge(1296000L) // from 7 to 30 days to keep refreshToken in the cookie and the cookie's duration can be the same as the token's duration.
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        final String redirectURL = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth/callback")
                .queryParam("accessToken", accessToken).build().toUriString();

        response.sendRedirect(redirectURL);
    }
}

