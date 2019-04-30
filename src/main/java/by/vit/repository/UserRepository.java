package by.vit.repository;

import by.vit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Users
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username from DataBase
     *
     * @param username of user
     * @return user
     */
    User findByUsername(String username);
}
