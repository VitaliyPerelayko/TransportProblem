package by.vit.repository;

import by.vit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Long> {
}
