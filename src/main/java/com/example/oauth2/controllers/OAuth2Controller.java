package com.example.oauth2.controllers;

import com.example.oauth2.abstractions.SecurityService;
import com.example.oauth2.abstractions.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final JwtService jwtService;
    private final SecurityService securityService;

    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(HttpServletResponse response) {
        final String jwt = this.jwtService.generateToken(UUID.randomUUID().toString(), "USER");

        final Cookie cookie = new Cookie("accessToken", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(5);
        cookie.setAttribute("SameSite", "Strict");

        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        final Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setAttribute("SameSite", "Strict");

        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/private")
    public String routePrivate() {
        final var userId = this.securityService.getUserId();
        return "private route " + userId;
    }

    @GetMapping("/role")
    public String role() {
        return "Voce conseguiu acessar pois tem cargo para isso!";
    }
}
