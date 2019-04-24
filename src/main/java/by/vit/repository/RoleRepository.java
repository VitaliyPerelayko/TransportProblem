package by.vit.repository;

import by.vit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Role
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Return true, if role with this name exist in database,
     * and false otherwise.
     *
     * @param name
     * @return true or false
     */
    boolean existsByName(String name);

    /**
     * Find role by name from DataBase
     *
     * @param name of role
     * @return role
     */
    Role findByName(String name);
}
