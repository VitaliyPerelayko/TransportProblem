package by.vit.dao.impl;

import by.vit.dao.RoadDAO;
import by.vit.model.Road;
import by.vit.model.RoadId;
import by.vit.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RoadDAOImpl extends GenericDAOImpl<Road, RoadId> implements RoadDAO {
    private volatile static RoadDAOImpl instance;

    private RoadDAOImpl() {
        super(Road.class);
    }

    /**
     * @return singleton instance of Dao
     */
    public static RoadDAOImpl getInstance() {
        if (instance == null) {
            synchronized (RoadDAOImpl.class) {
                if (instance == null) {
                    instance = new RoadDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Find All Roads
     * HQl implementation
     *
     * @return List<Road>
     */
    @Override
    public List<Road> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Road";
            Query query = session.createQuery(hql, Road.class);
            return query.list();
        }
    }
}
