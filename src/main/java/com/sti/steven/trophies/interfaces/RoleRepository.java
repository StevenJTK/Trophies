package com.sti.steven.trophies.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sti.steven.trophies.product.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
    Role userRole = new Role("USER");
    Role adminRole = new Role ("ADMIN");
}
