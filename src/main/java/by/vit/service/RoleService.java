package by.vit.service;

import by.vit.model.Role;

public interface RoleService {
    Role saveRole(Role role);

    Role findRole(Long id) throws Exception;

    Role getRole(Long id);

    void deleteRole(Role role);
}
