package by.vit.service;

import by.vit.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role save(Role role);

    Role update(Role role);

    Role findById(Long id);

    Role getById(Long id);

    void delete(Role role);

    void deleteById(Long id);
}
