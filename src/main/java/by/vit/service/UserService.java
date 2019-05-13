package by.vit.service;

import by.vit.model.User;

import java.util.List;

/**
 * service layer for User entity
 */
public interface UserService {

    /**
     * Find all users from database.
     *
     * @return List<User>
     */
    List<User> findAll();

    /**
     * Save new entity User.
     *
     * @param user user entity
     * @return saved entity
     */
    User save(User user);

    /**
     * Update entity User.
     *
     * @param user user entity
     * @return updated entity
     */
    User update(User user);

    /**
     * Retrieves a User by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if User none found
     */
    User findById(Long id);

    /**
     * Retrieves a User by its username.
     *
     * @param username username of user
     * @return the entity with the given username
     */
    User findByUsername(String username);

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    User getById(Long id);

    /**
     * Deletes a given entity.
     *
     * @param user user entity
     */
    void delete(User user);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(Long id);
}
