package com.example.oauth2.controllers;

import com.example.oauth2.abstractions.CurrentUserService;
import com.example.oauth2.abstractions.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final JwtService jwtService;
    private final CurrentUserService currentUserService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthorizationResponse> login(HttpServletResponse response) {
        final String accessToken = this.jwtService.generateAccessToken(UUID.randomUUID().toString(), "USER", Collections.emptyList());
        final String refreshToken = UUID.randomUUID().toString();

        final ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/api/auth/refresh")
                .maxAge(Duration.ofDays(15)) // from 7 to 30 days to keep refreshToken in the cookie
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().body(AuthorizationResponse.from(accessToken));
    }

    @GetMapping("/private")
    public String routePrivate() {
        final var userId = this.currentUserService.getUserId();
        return "private route " + userId;
    }

    @GetMapping("/role")
    public String role() {
        return "Voce conseguiu acessar pois tem cargo para isso!";
    }
}
