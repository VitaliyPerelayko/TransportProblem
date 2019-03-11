package by.vit.dao.impl;

import by.vit.dao.CarDAO;
import by.vit.model.Car;

public class CarDAOImpl extends GenericDAOImpl<Car,Long> implements CarDAO {
    private volatile static CarDAOImpl instance;

    private CarDAOImpl() {
        super(Car.class);
    }

    /**
     * @return singleton instance of Dao
     */
    public static CarDAOImpl getInstance() {
        if (instance == null) {
            synchronized (CarDAOImpl.class){
                if(instance==null){
                    instance = new CarDAOImpl();
                }
            }
        }
        return instance;
    }
}
