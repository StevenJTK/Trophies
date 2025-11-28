package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.interfaces.UserRepository;
import com.sti.steven.trophies.product.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userRegistered = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistered);
    }

    @GetMapping("/userDetails/{id}")
    public ResponseEntity<User> userDetails(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
