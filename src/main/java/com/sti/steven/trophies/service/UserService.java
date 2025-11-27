package com.sti.steven.trophies.service;

import com.sti.steven.trophies.interfaces.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sti.steven.trophies.product.Role;
import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.interfaces.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if(userRepository.existsByUsername(user.getUsername()))
            throw new IllegalArgumentException("Username already exists.");

        if(userRepository.existsByEmail(user.getEmail()))
            throw new IllegalArgumentException("Email already exists.");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void giveAdminToUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        Optional <Role> adminRole = roleRepository.findByRoleName("ADMIN");
        if(adminRole.isEmpty()) {
            throw new IllegalArgumentException("Role does not exist.");
        }

        boolean hasRole = user.getRoles().stream()
                .anyMatch(r -> Objects.equals(r.getRoleName(), adminRole.get().getRoleName()));

        if (!hasRole) {
            user.getRoles().add(adminRole.get());
            userRepository.save(user);
        }
    }

    public void removeRoleFromUser(User user, Role role) {
        if (user == null || role == null) {
            throw new IllegalArgumentException("User or role cannot be null.");
        }

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User does not have this role.");
        }
    }

    public Optional<User> findByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }

        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        return userRepository.findByEmail(email);
    }

    public Optional<Role> findRoleByName(String roleName) {
        if (roleName == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        return roleRepository.findByRoleName(roleName);
    }
}
