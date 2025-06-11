package com._7.hr.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JWTokenProvider {
    @Value("${app.jwt.secret}")
    private String jwtSecretString;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationInMs;
    private SecretKey jwtSecretKey;

    private static final Logger logger = LoggerFactory.getLogger(JWTokenProvider.class);

    @PostConstruct
    protected void init() {
        if (jwtSecretString == null || jwtSecretString.length() < 32) {
            logger.warn(
                    "WARNING: JWT Secret is not configured or too short. Using a temporary insecure key for development.");

            jwtSecretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        } else {
            jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
        }
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtExpirationInMs);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .claim("tid", username.split("/")[0])
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(jwtSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            logger.error("Invalid JWT Token provided", ex);
            ;
        }

        return false;
    }

}
