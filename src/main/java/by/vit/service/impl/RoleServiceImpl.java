package by.vit.service.impl;

import by.vit.model.Role;
import by.vit.repository.RoleRepository;
import by.vit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for Role entity.
 */
@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Save new entity and update persist entity Role.
     *
     * @param role entity
     * @return saved or updated entity from database
     */
    @Override
    public Role saveRole(Role role) {
        Role savedEntity = getRoleRepository().save(role);
        return savedEntity;
    }

    /**
     * Retrieves a Role by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if Role none found
     */
    @Override
    public Role findRole(Long id) throws Exception {
        Optional<Role> role = getRoleRepository().findById(id);
        if (role.isPresent()) {
            return role.get();
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
    public Role getRole(Long id) {
        Role role = getRoleRepository().getOne(id);
        return role;
    }

    /**
     * Deletes a given entity.
     *
     * @param role
     */
    @Override
    public void deleteRole(Role role) {
        getRoleRepository().delete(role);
    }
}
