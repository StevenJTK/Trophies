package com.sti.steven.trophies.util;

import com.sti.steven.trophies.product.Role;
import com.sti.steven.trophies.product.Roles;
import com.sti.steven.trophies.product.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

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
public class JWTUtil {

    private static final Logger logger = LoggerFactory .getLogger(JWTUtil.class);
    private final String base64EncodedSecretKey = "U2VjdXJlQXBpX1NlY3JldEtleV9mb3JfSFMyNTYwX3NlY3JldF9wcm9qZWN0X2tleV9leGFtcGxl" ;

    private final byte[] keyBytes = Base64.getDecoder().decode(base64EncodedSecretKey );
    private final SecretKey key = Keys.hmacShaKeyFor (keyBytes);
    private final int jwtExpirationMs = (int) TimeUnit.HOURS.toMillis(1);


    public String generateToken(User user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toList();

        String token = Jwts.builder()
                .subject(user.getUsername ())
                .claim("authorities" , roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis () + jwtExpirationMs ))
                .signWith(key)
                .compact();
        logger.info("Token generated succesfully for user: {}", user.getUsername());
        return token;
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();
            logger.debug("Extracted username '{}' from token", username);
            return username;
        }   catch (Exception e) {
            logger.warn("Failed to extract username from token", e.getMessage());
            return null;
        }
    }

    public boolean validateJwtToken (String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);
            logger.debug("JWT validation succeeded");
            return true;
        } catch (Exception e) {
            logger.error("JWT validation failed: {} ", e.getMessage());
        }
        return false;
    }
    public Set<Roles> getRolesFromToken (String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        List<?> authoritiesClaim = claims.get("authorities", List.class);

        if(authoritiesClaim == null || authoritiesClaim.isEmpty()) {
            logger.warn("No authorities found in token.");
            return Set.of();
        }

        Set<Roles> roles = authoritiesClaim.stream()
                .filter(String.class::isInstance )
                .map(String.class::cast)
                .map(role -> role.replace("ROLE_", ""))
                .map(String::toUpperCase )
                .map(Roles::valueOf)
                .collect(Collectors.toSet());

        logger.debug("Extracted roles from JWT tokens: {}", roles);
        return roles;

    }
}
