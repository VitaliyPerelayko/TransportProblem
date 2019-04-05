package by.vit.repository;

import by.vit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Role
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
