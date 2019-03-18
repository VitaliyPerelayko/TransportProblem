package by.vit.dao.impl;

import by.vit.dao.RoleDAO;
import by.vit.model.Role;

public class RoleDAOImpl extends GenericDAOImpl<Role,Long> implements RoleDAO {
    private volatile static RoleDAOImpl instance;

    private RoleDAOImpl() {
        super(Role.class);
    }

    /**
     * @return singleton instance of Dao
     */
    public static RoleDAOImpl getInstance() {
        if (instance == null) {
            synchronized (RoleDAOImpl.class){
                if(instance==null){
                    instance = new RoleDAOImpl();
                }
            }
        }
        return instance;
    }
}
