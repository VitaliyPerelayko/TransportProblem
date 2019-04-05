package by.vit.repository;

import by.vit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Users
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
