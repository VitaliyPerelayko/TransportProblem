package by.vit.repository;

import by.vit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role,Long> {
}
