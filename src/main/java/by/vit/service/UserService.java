package by.vit.service;

import by.vit.model.User;

public interface UserService {
    User saveUser(User user);

    User findUser(Long id) throws Exception;

    User getUser(Long id);

    void deleteUser(User user);
}
