package com.sti.steven.trophies.config;

import com.sti.steven.trophies.interfaces.RoleRepository;
import com.sti.steven.trophies.entity.Role;
import com.sti.steven.trophies.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleStartupRunner implements CommandLineRunner {

    RoleRepository roleRepository;

    @Autowired
    public RoleStartupRunner(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        onAppStart();
    }

    public void onAppStart() {
       for(Roles roles : Roles.values()) {
           Optional<Role> role = roleRepository.findByRoleName(roles.name());

           if(role.isEmpty()) {
               Role newRole = new Role();
               newRole.setRoleName(roles.name());
               roleRepository.save(newRole);
               System.out.println("Created role: " + roles.name());
           }
       }
    }
}
