package com.sti.steven.trophies.service;

import com.sti.steven.trophies.dto.UserDTO;
import com.sti.steven.trophies.game.Trophy;
import com.sti.steven.trophies.interfaces.RoleRepository;
import com.sti.steven.trophies.interfaces.TrophyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sti.steven.trophies.product.Role;
import com.sti.steven.trophies.product.User;
import com.sti.steven.trophies.interfaces.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrophyRepository trophyRepository;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, TrophyRepository trophyRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.trophyRepository = trophyRepository;
    }

    @Autowired
    AuthenticationManager authManager;

    public User createNewUser(UserDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if(userRepository.existsByUsername(dto.getUsername()))
            throw new IllegalArgumentException("Username already exists.");

        if(userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Email already exists.");

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());

        Optional<Role> userRole = roleRepository.findByRoleName("USER");

        if(userRole.isEmpty()) {
            throw new IllegalArgumentException("Role does not exist.");
        }

        user.getRoles().add(userRole.get());
        return userRepository.save(user);
    }


    @Transactional
    public User deleteUser(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        userRepository.delete(user);
        return user;
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

    public String verify(String username) {
        User dbUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (dbUser.getIsEnabled()) {
            return "User succesfully enabled.";
        }
        dbUser.setIsEnabled(true);
        userRepository.save(dbUser);
        return "User successfully verified.";
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void completeTrophyForUser(Integer userId, Integer trophyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        Trophy trophy = trophyRepository.findById(trophyId)
                .orElseThrow(() -> new IllegalArgumentException("Trophy not found"));

        user.completeTrophy(trophy);
        userRepository.save(user);
    }

    public List<User> getPendingUsers() {
        return userRepository.findAll()
                .stream()
                .filter(u -> !u.getIsEnabled())
                .toList();
    }
}
