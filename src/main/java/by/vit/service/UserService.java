package by.vit.service;

import by.vit.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);

    User update(User user);

    User findById(Long id);

    User getById(Long id);

    void delete(User user);

    void deleteById(Long id);
}
