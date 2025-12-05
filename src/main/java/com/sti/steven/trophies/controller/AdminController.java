package com.sti.steven.trophies.controller;

import com.sti.steven.trophies.dto.UserDTO;
import com.sti.steven.trophies.interfaces.RoleRepository;
import com.sti.steven.trophies.interfaces.UserRepository;
import com.sti.steven.trophies.product.Role;
import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.security.jwt.JwtAuthenticationFilter;
import com.sti.steven.trophies.service.JwtService;
import com.sti.steven.trophies.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(UserRepository userRepository, JwtService jwtService, UserService userService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@PathVariable Integer id, @RequestBody Set<String> roleNames) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Set<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByRoleName(roleName).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Role" + roleName + " does not exist")))
                .collect(Collectors.toSet());

        user.getRoles().clear();
        user.getRoles().addAll(roles);

        userRepository.save(user);
        logger.info("This user's role '{}' (ID {}) is updated to: {}", user.getUsername(), id, roleNames);
        return ResponseEntity.ok("Admin role has been provided.");
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findPendingUsers() {
        return ResponseEntity.ok(userService.getPendingUsers());
    }

    @PostMapping("/verify/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> verifyUser(@PathVariable String username) {
        String result = userService.verify(username);
        return ResponseEntity.ok(result);
    }

    // Mapping does delete the user, however postman shows 403 forbidden?
    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @PostMapping("{userId}/{trophyId}/complete")
    @PreAuthorize("hasRole.'ADMIN'")
    public ResponseEntity<String> completeTrophy(@PathVariable Integer userId,
                                                 @PathVariable Integer trophyId) {
        userService.completeTrophyForUser(userId, trophyId);
        return ResponseEntity.ok("Congratulations! Trophy unlocked.");
    }
}
