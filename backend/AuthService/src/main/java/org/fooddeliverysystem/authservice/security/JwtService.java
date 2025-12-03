package org.fooddeliverysystem.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.fooddeliverysystem.authservice.model.Role;
import org.fooddeliverysystem.authservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Getter
@Setter
public class JwtService {

    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    public JwtService(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
            @Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
            @Value("${security.jwt.issuer}") String issuer
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    // ------------------------------
    //  ACCESS TOKEN
    // ------------------------------
    public String generateAccessToken(User user) {
        Instant now = Instant.now();

        List<String> roles = user.getRoles() == null
                ? List.of()
                : user.getRoles().stream()
                .map(Role::getName)
                .toList();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claims(Map.of(
                        "email", user.getEmail(),
                        "typ", "access",
                        "roles", roles
                ))
                .signWith(key)
                .compact();
    }

    // ------------------------------
    //  REFRESH TOKEN
    // ------------------------------
    public String generateRefreshToken(User user, String jti) {
        Instant now = Instant.now();

        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ", "refresh")
                .signWith(key)
                .compact();
    }

    // ------------------------------
    //  PARSE TOKEN
    // ------------------------------
    public Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid or expired JWT", e);
        }
    }

    public boolean isAccessToken(String token) {
        return "access".equals(parseToken(token).getPayload().get("typ"));
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equals(parseToken(token).getPayload().get("typ"));
    }

    public UUID getUserId(String token) {
        return UUID.fromString(parseToken(token).getPayload().getSubject());
    }

    public List<String> getRoles(String token) {
        return parseToken(token).getPayload().get("roles", List.class);
    }

    public String getJti(String token) {
        return parseToken(token).getPayload().getId();
    }
}
