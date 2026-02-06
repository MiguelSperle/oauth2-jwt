package com.example.oauth2.controllers;

import com.example.oauth2.abstractions.CurrentUserService;
import com.example.oauth2.abstractions.JwtService;
import com.example.oauth2.controllers.dtos.AuthorizationResponse;
import com.example.oauth2.csrf.CsrfToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final JwtService jwtService;
    private final CurrentUserService currentUserService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthorizationResponse> login(HttpServletRequest request, HttpServletResponse response) {
        final String accessToken = this.jwtService.generateAccessToken(UUID.randomUUID().toString(), "USER", Collections.emptyList());
        final String refreshToken = UUID.randomUUID().toString();

        final ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("none") // backend domain is different of frontend domain that's why is None
                .path("/auth/refresh")
                .maxAge(1296000) // from 7 to 30 days to keep refreshToken in the cookie ( in seconds )
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok().body(AuthorizationResponse.from(accessToken));
    }

    @GetMapping("/private")
    public String routePrivate() {
        final var userId = this.currentUserService.getUserId();
        return "private route " + userId;
    }

    @PatchMapping("/role")
    public String role() {
        return "Voce conseguiu atualizar pois tem cargo para isso!";
    }

    @PostMapping("/auth/refresh")
    @CsrfToken
    public ResponseEntity<String> refresh(@CookieValue(value = "refresh_token") String refreshToken) {
        System.out.println("Refresh token: " + refreshToken);
        return ResponseEntity.ok().build();
    }
}
