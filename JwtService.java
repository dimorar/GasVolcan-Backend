package com.gasvolcan.gasvolcan_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(
        @Value("${app.jwt.secret}") String secret
    ) {
        this.key = Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generar(String email) {
        Date ahora = new Date();

        return Jwts.builder()
            .subject(email)
            .issuedAt(ahora)
            .expiration(new Date(ahora.getTime() + 3600000))
            .signWith(key)
            .compact();
    }

    public String extraerEmail(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean esValido(String token) {
        try {
            extraerEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
