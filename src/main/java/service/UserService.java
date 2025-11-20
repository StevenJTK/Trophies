package service;

import interfaces.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import product.Role;
import product.User;
import interfaces.UserRepository;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public User createNewUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return userRepository.save(user);
    }

    public void giveRoleToUser(User user, Role role) {
        if (user == null || role == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    public void removeRoleFromUser(User user, Role role) {
        if (user == null || role == null) {
            throw new IllegalArgumentException("User or role cannot be null.");
        }

        if (!user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User does not have this role.");
        }
    }

    public User findByUsername(String username) {
        User user;

        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User was not found.");
        }

        return user;
    }

    public User findByEmail(String email) {
        User user;

        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User was not found.");
        }

        return user;
    }
}
