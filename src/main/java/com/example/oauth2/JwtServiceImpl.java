package com.example.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    @Override
    public String generateToken(String userId, String role) {
        final Instant now = Instant.now();
        final long expiresIn = 10L;

        final var claims = JwtClaimsSet.builder()
                .issuer("oauth2-project")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .subject(userId)
                .claim("role", role)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
