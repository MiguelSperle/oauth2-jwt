package com.example.oauth2.controllers;

import com.example.oauth2.controllers.dtos.CsrfTokenResponse;
import com.example.oauth2.csrf.CsrfToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CsrfTokenController {
    @PostMapping("/auth/csrf-token")
    public ResponseEntity<CsrfTokenResponse> csrfToken(HttpServletResponse response) {
        final String csrfToken = UUID.randomUUID().toString();

        final ResponseCookie csrfTokenCookie = ResponseCookie.from(CsrfToken.CSRF_TOKEN_COOKIE, csrfToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("none")
                .path("/auth/refresh")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, csrfTokenCookie.toString());

        return ResponseEntity.ok().body(CsrfTokenResponse.from(csrfToken));
    }
}
