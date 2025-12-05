package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.dto.UserDTO;
import com.sti.steven.trophies.interfaces.RoleRepository;
import com.sti.steven.trophies.interfaces.UserRepository;
import com.sti.steven.trophies.product.Role;
import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.security.jwt.JwtAuthenticationFilter;
import com.sti.steven.trophies.service.JwtService;
import com.sti.steven.trophies.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sti.steven.trophies.security.jwt.JwtUtil;


import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtService jwtService, UserService userService,
                          AuthenticationManager authenticationManager, RoleRepository roleRepository1) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository1;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody(required = false) UserDTO dto, HttpServletResponse response) {
        if (dto == null || dto.getUsername() == null || dto.getPassword() == null) {
            return ResponseEntity.badRequest().body("User data is missing.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = userService.findByUsername(dto.getUsername()).orElseThrow(() -> new IllegalStateException("User not found."));
        logger.info("User '{}' found.", user.getUsername());

        String token = jwtService.generateToken(user);

        Cookie cookie = new Cookie("JWT_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", user.getUsername());
        responseBody.put("email", user.getEmail());

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO dto) {
        if(dto == null || dto.getUsername() == null || dto.getPassword() == null) {
            return ResponseEntity.badRequest().body("Fields are missing.");
        }

        if (userService.usernameExists(dto.getUsername()) || userService.emailExists(dto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists.");
        }

        User registerUser = userService.createNewUser(dto);

        String token = jwtService.generateToken(registerUser);

        if(token == null) {
            logger.info("Token was not generated.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token.");

        }

        if(registerUser == null) {
            logger.info("Could not create new user.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed.");
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", registerUser.getUsername());
        responseBody.put("email", registerUser.getEmail());
        responseBody.put("token", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        String token = authHeader.substring(7);
        return ResponseEntity.ok("Logged out.");

    }
}
