package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.dto.UserDTO;
import com.sti.steven.trophies.interfaces.UserRepository;
import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user/register")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

   /* @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        User user = userService.createNewUser(dto);
        UserDTO responseDTO = new UserDTO(user.getUsername(), null, user.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/userDetails/{id}")
    public ResponseEntity<User> userDetails(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    } */

}
