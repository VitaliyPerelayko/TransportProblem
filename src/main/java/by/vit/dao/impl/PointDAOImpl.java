package by.vit.dao.impl;

import by.vit.dao.PointDAO;
import by.vit.model.Point;
import by.vit.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class PointDAOImpl extends GenericDAOImpl<Point,Long> implements PointDAO {

    private volatile static PointDAOImpl instance;

    private PointDAOImpl() {
        super(Point.class);
    }

    /**
     * @return singleton instance of Dao
     */
    public static PointDAOImpl getInstance() {
        if (instance == null) {
            synchronized (PointDAOImpl.class){
                if(instance==null){
                    instance = new PointDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Get point by name from DataBase
     * @param name of point
     * @return point
     */
    @Override
    public Point getPointByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Point point WHERE point.name = :name";
            Query query = session.createQuery(hql);
            query.setParameter("name",name);
            return (Point)query.getSingleResult();
        }
    }
}
