package by.vit.service.impl;

import by.vit.model.User;
import by.vit.repository.UserRepository;
import by.vit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for User entity.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save new entity and update persist entity User.
     *
     * @param user entity
     * @return saved or updated entity from database
     */
    @Override
    public User saveUser(User user) {
        User savedEntity = getUserRepository().save(user);
        return savedEntity;
    }

    /**
     * Retrieves a User by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if User none found
     */
    @Override
    public User findUser(Long id) throws Exception {
        Optional<User> user = getUserRepository().findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new Exception("massage");
    }

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    @Override
    public User getUser(Long id) {
        User user = getUserRepository().getOne(id);
        return user;
    }

    /**
     * Deletes a given entity.
     *
     * @param user
     */
    @Override
    public void deleteUser(User user) {
        getUserRepository().delete(user);
    }
}
