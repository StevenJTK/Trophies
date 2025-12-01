package com.sti.steven.trophies.service;

import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.security.jwt.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user);
    }

    public String getUsernameFromToken(String token) {
        return jwtUtil.getUsernameFromToken(token);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateJwtToken(token);
    }
}
