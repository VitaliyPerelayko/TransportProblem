package by.vit.repository;

import by.vit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Users
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Return true, if user with this username exist in database,
     * and false otherwise.
     *
     * @param username
     * @return true or false
     */
    boolean existsByUsername(String username);

    /**
     * Find user by username from DataBase
     *
     * @param username of user
     * @return user
     */
    User findByUsername(String username);
}
