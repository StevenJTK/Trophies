package com.sti.steven.trophies.security.jwt;

import com.sti.steven.trophies.product.Role;
import com.sti.steven.trophies.product.Roles;
import com.sti.steven.trophies.product.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final String base64EncodedSecretKey = "w4F7n8YtRzP9sX1vKj5Lq6Mv2BdVfG7hQyZrNc8Ua0E=";
    private final SecretKey key;
    private final long jwtExpirationMs = TimeUnit.HOURS.toMillis(1);

    public JwtUtil() {
        byte[] keyBytes = Base64.getDecoder().decode(base64EncodedSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toList();

        // Deprication indicated on my end - let me know if this could have been done better!
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getRoles().stream()
                        .map(Role::getRoleName)
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + getJwtExpirationMs()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        // Attempt to verify JWS claims via key
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return jws.getBody().getSubject();
        } catch (JwtException e) {
            // Login fails we return null and a log warning
            logger.warn("Failed to get username from JWT: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            logger.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    public SecretKey getKey() {
        return key;
    }

    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public Set<Roles> getRolesFromToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            List<?> authoritiesClaim = jws.getBody().get("authorities", List.class);
            if (authoritiesClaim == null) {
                return Set.of();
            }

            return authoritiesClaim.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .map(role -> role.replace("ROLE_", ""))
                    .map(String::toUpperCase)
                    .map(Roles::valueOf)
                    .collect(Collectors.toSet());

        } catch (JwtException e) {
            logger.warn("Failed to get roles from JWT: {}", e.getMessage());
            return Set.of();
        }
    }
}
