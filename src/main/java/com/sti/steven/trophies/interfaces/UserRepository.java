package com.sti.steven.trophies.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sti.steven.trophies.product.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional <User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
