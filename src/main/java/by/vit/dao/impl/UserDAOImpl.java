package by.vit.dao.impl;

import by.vit.dao.UserDAO;
import by.vit.model.User;

public class UserDAOImpl extends GenericDAOImpl<User,Long> implements UserDAO {
    private volatile static UserDAOImpl instance;

    private UserDAOImpl() {
        super(User.class);
    }

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOImpl.class){
                if(instance==null){
                    instance = new UserDAOImpl();
                }
            }
        }
        return instance;
    }
}
