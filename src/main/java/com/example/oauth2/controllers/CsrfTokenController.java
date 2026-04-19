package com.example.oauth2.controllers;

import com.example.oauth2.controllers.dtos.CsrfTokenResponse;
import com.example.oauth2.csrf.CsrfToken;
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
    public ResponseEntity<CsrfTokenResponse> csrfToken() {
        final String csrfToken = UUID.randomUUID().toString();

        final ResponseCookie csrfTokenCookie = ResponseCookie.from(CsrfToken.CSRF_TOKEN_COOKIE, csrfToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("none") // backend domain is different of frontend domain that's why is "none"
                .path("/auth/refresh")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, csrfTokenCookie.toString()).body(CsrfTokenResponse.from(
                csrfToken,
                CsrfToken.CSRF_TOKEN_HEADER
        ));
    }
}
