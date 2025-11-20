package interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
