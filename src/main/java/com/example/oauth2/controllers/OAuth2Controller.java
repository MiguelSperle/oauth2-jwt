package com.example.oauth2.controllers;

import com.example.oauth2.abstractions.services.JwtTokenService;
import com.example.oauth2.controllers.dtos.AuthorizationResponse;
import com.example.oauth2.csrf.CsrfToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final JwtTokenService jwtTokenService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthorizationResponse> login() {
        final String accessToken = this.jwtTokenService.generateAccessToken(UUID.randomUUID().toString(), "USER", List.of());
        final String refreshToken = UUID.randomUUID().toString();

        final ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("none") // backend domain is different of frontend domain that's why is "none"
                .path("/auth/refresh")
                .maxAge(1296000L) // from 7 to 30 days to keep refreshToken in the cookie and the cookie's duration can be the same as the token's duration.
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(AuthorizationResponse.from(accessToken));
    }

    @GetMapping("/private")
    public String routePrivate(@AuthenticationPrincipal Jwt jwt) {
        final var userId = jwt.getSubject();
        return "private route " + userId;
    }

    @PatchMapping("/role")
    public String role() {
        return "Voce conseguiu atualizar pois tem cargo para isso!";
    }

    @PostMapping("/auth/refresh")
    @CsrfToken
    public ResponseEntity<String> refresh(@CookieValue(value = "refresh-token") String refreshToken) {
        System.out.println("Refresh token: " + refreshToken);
        return ResponseEntity.ok().build();
    }
}
