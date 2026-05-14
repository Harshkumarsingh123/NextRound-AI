package com.nextround.auth_service.config;

import com.nextround.auth_service.entity.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key getKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    /*
     * GENERATE JWT TOKEN
     */
    public String generateToken(User user) {

        return Jwts.builder()

                .setSubject(user.getEmail())

                .claim(
                        "userId",
                        user.getId()
                )

                .claim(
                        "fullName",
                        user.getFullName()
                )

                .claim(
                        "role",
                        user.getRole()
                )

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )

                .signWith(
                        getKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    /*
     * EXTRACT EMAIL
     */
    public String extractEmail(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getKey())

                .build()

                .parseClaimsJws(token)

                .getBody()

                .getSubject();
    }

    /*
     * EXTRACT USER ID
     */
    public Long extractUserId(String token) {

        Claims claims = Jwts.parserBuilder()

                .setSigningKey(getKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

        return claims.get(
                "userId",
                Long.class
        );
    }

    /*
     * VALIDATE TOKEN
     */
    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()

                    .setSigningKey(getKey())

                    .build()

                    .parseClaimsJws(token);

            return true;

        } catch (JwtException e) {

            return false;
        }
    }
}