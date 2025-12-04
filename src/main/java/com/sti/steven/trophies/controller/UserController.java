package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.dto.UserDTO;
import com.sti.steven.trophies.interfaces.RoleRepository;
import com.sti.steven.trophies.interfaces.UserRepository;
import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.service.JwtService;
import com.sti.steven.trophies.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, JwtService jwtService, UserService userService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate token.");
        }

        if(registerUser == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User creation failed.");
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", registerUser.getUsername());
        responseBody.put("email", registerUser.getEmail());
        responseBody.put("token", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody(required = false) UserDTO dto) {
        if(dto == null || dto.getUsername() == null || dto.getPassword() == null) {
            return ResponseEntity.badRequest().body("User data is missing.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = userService.findByUsername(dto.getUsername()).orElseThrow(() -> new IllegalStateException("User not found."));

        String token = jwtService.generateToken(user);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("username", user.getUsername());
        responseBody.put("email", user.getEmail());
        responseBody.put("token", token);

        return ResponseEntity.ok(responseBody);
    }



}
