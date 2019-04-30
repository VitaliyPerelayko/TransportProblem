package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.User;
import by.vit.repository.UserRepository;
import by.vit.service.RoleService;
import by.vit.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of service layer for User entity.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LocalizedMessageSource localizedMessageSource;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, LocalizedMessageSource localizedMessageSource,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.localizedMessageSource = localizedMessageSource;
        this.roleService = roleService;
    }

    /**
     * Find all users from database.
     *
     * @return List<User>
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Save new entity User.
     *
     * @param user entity
     * @return saved entity from database
     */
    @Override
    public User save(User user) {
        validate(user.getId() != null,
                localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        return saveAndFlush(user);
    }

    /**
     * Update entity User.
     *
     * @param user entity
     * @return updated entity from database
     */
    @Override
    public User update(User user) {
        validate(user.getId() == null,
                localizedMessageSource.getMessage("error.user.haveNoId", new Object[]{}));
        return saveAndFlush(user);
    }

    /**
     * Retrieves a User by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if User none found
     */
    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        validate(!(user.isPresent()), localizedMessageSource.getMessage("error.user.notExist", new Object[]{}));
        return user.get();
    }

    /**
     * Retrieves a User by its username.
     *
     * @param username
     * @return the entity with the given username
     */
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
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
    public User getById(Long id) {
        User user = userRepository.getOne(id);
        return user;
    }

    /**
     * Deletes a given entity.
     *
     * @param user
     */
    @Override
    public void delete(User user) {
        final Long id = user.getId();
        validate(id == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        findById(id);
        userRepository.delete(user);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private User saveAndFlush(User user) {
        user.getRoles().forEach(role -> {
            validate(role == null || role.getId() == null,
                    localizedMessageSource.getMessage("error.user.roles.isNull", new Object[]{}));
            role.setName(roleService.findById(role.getId()).getName());
        });
        return userRepository.saveAndFlush(user);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
