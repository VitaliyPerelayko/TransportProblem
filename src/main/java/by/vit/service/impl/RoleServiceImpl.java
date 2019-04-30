package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Role;
import by.vit.repository.RoleRepository;
import by.vit.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of service layer for Role entity.
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final LocalizedMessageSource localizedMessageSource;

    private final RoleRepository roleRepository;

    public RoleServiceImpl(LocalizedMessageSource localizedMessageSource, RoleRepository roleRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.roleRepository = roleRepository;
    }

    /**
     * Find all roles from database.
     *
     * @return List<Role>
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Save new entity Role.
     *
     * @param role entity
     * @return saved entity from database
     */
    @Override
    public Role save(Role role) {
        validate(role.getId() != null,
                localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        validate(roleRepository.existsByName(role.getName()),
                localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    /**
     * Update entity Role.
     *
     * @param role entity
     * @return updated entity from database
     */
    @Override
    public Role update(Role role) {
        final Long id = role.getId();
        validate(id == null,
                localizedMessageSource.getMessage("error.role.haveNoId", new Object[]{}));
        final Role duplicateRole = roleRepository.findByName(role.getName());
        findById(id);
        final boolean isDuplicateExists = duplicateRole != null && !Objects.equals(duplicateRole.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }


    /**
     * Retrieves a Role by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Role none found
     */
    @Override
    public Role findById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        validate(!(role.isPresent()), localizedMessageSource.getMessage("error.role.notExist", new Object[]{}));
        return role.get();
    }

    /**
     * Retrieves a Role by its name.
     *
     * @param name
     * @return the entity with the given name
     */
    @Override
    public Role findByName(String name){
        return roleRepository.findByName(name);
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
    public Role getById(Long id) {
        Role role = roleRepository.getOne(id);
        return role;
    }

    /**
     * Deletes a given entity.
     *
     * @param role
     */
    @Override
    public void delete(Role role) {
        final Long id = role.getId();
        validate(role.getId() == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        findById(id);
        roleRepository.delete(role);
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
        roleRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
