package com.example.oauth2.services;

import com.example.oauth2.abstractions.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    public JwtServiceImpl(final JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${app.security.jwt.issuer}")
    private String issuer;

    @Override
    public String generateAccessToken(final String userId, final String role, final List<String> permissions) {
        final Instant now = Instant.now(); // instant is UTC and with that, it ignores local time zone ( it is a universal watch )

        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(this.issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(900)) // from 5 until 15 minutes for the jwt expire
                .subject(userId)
                .claim("role", role)
                .claim("permissions", permissions)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
