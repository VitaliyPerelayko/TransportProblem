package by.vit.service;

import by.vit.model.Role;

import java.util.List;

/**
 * Service layer for Role entity.
 */
public interface RoleService {

    /**
     * Find all roles from database.
     *
     * @return List<Role>
     */
    List<Role> findAll();

    /**
     * Save new entity Role.
     *
     * @param role role entity
     * @return saved entity
     */
    Role save(Role role);

    /**
     * Update entity Role.
     *
     * @param role role entity
     * @return updated entity
     */
    Role update(Role role);

    /**
     * Retrieves a Role by its id.
     *
     * @param id id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Role none found
     */
    Role findById(Long id);

    /**
     * Retrieves a Role by its name.
     *
     * @param name name of role
     * @return the entity with the given name
     */
    Role findByName(String name);

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    Role getById(Long id);

    /**
     * Deletes a given entity.
     *
     * @param role role entity
     */
    void delete(Role role);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(Long id);
}
