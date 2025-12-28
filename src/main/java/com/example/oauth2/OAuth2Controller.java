package com.example.oauth2;

import lombok.RequiredArgsConstructor;
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
    public String login() {
        return this.jwtService.generateToken(UUID.randomUUID().toString(), "USER");
    }

    @GetMapping("/private")
    public String routePrivate() {
        final var userId = this.securityService.getCurrentUserId();
        return "private route " + userId;
    }

    @GetMapping("/role")
    public String role() {
        return "Voce conseguiu acessar pois tem cargo para isso!";
    }
}
