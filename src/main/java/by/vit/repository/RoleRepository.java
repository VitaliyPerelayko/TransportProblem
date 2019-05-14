package by.vit.repository;

import by.vit.model.Role;
import by.vit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Repository layer for Role
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Return true, if role with this name exist in database,
     * and false otherwise.
     *
     * @param name name of role
     * @return true or false
     */
    boolean existsByName(String name);

    /**
     * Find role by name from DataBase
     *
     * @param name name of role
     * @return role
     */
    Role findByName(String name);

    /**
     * Find all roles of given user from DataBase
     *
     * @param  user user entity
     * @return set of roles
     */
    Set<Role> findAllByUsers(User user);
}
