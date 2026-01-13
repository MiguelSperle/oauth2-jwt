package com.example.oauth2.services;

import com.example.oauth2.abstractions.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtEncoder jwtEncoder;

    @Value("${spring.api.jwt.issuer}")
    private String issuer;

    @Override
    public String generateAccessToken(
            String userId,
            String role,
            List<String> permissions
    ) {
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
