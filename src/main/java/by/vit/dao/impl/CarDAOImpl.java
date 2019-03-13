package by.vit.dao.impl;

import by.vit.dao.CarDAO;
import by.vit.model.Car;
import by.vit.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CarDAOImpl extends GenericDAOImpl<Car, Long> implements CarDAO {
    private volatile static CarDAOImpl instance;

    private CarDAOImpl() {
        super(Car.class);
    }

    /**
     * @return singleton instance of Dao
     */
    public static CarDAOImpl getInstance() {
        if (instance == null) {
            synchronized (CarDAOImpl.class) {
                if (instance == null) {
                    instance = new CarDAOImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Get Car by restriction of tonnage
     *
     * @param restrictionTonnage restriction in the query
     * @param mass
     * @return list<CarModel>
     */
    @Override
    public List<Car> carModelByRestriction(restriction restrictionTonnage, Double mass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Car car JOIN FETCH car.carModel";
            switch (restrictionTonnage) {
                case EQUAL: {
                    hql = "FROM Car car JOIN FETCH car.carModel WHERE car.carModel.tonage = :mass";
                    break;
                }
                case LESS: {
                    hql = "FROM Car car JOIN FETCH car.carModel WHERE car.carModel.tonage < :mass";
                    break;
                }
                case MORE: {
                    hql = "FROM Car car JOIN FETCH car.carModel WHERE car.carModel.tonage > :mass";
                    break;
                }
            }
            Query query = session.createQuery(hql);
            if (restrictionTonnage != restriction.NO) {
                query.setParameter("mass", mass);
            }
            return query.list();
        }

    }
}
