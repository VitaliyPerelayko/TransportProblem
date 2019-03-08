package by.vit.dao.impl;

import by.vit.dao.CarModelDAO;
import by.vit.model.CarModel;

public class CarModelDAOImpl extends GenericDAOImpl<CarModel,Long> implements CarModelDAO {
    private volatile static CarModelDAOImpl instance;

    private CarModelDAOImpl() {
        super(CarModel.class);
    }

    public static CarModelDAOImpl getInstance() {
        if (instance == null) {
            synchronized (CarModelDAOImpl.class){
                if(instance==null){
                    instance = new CarModelDAOImpl();
                }
            }
        }
        return instance;
    }
}
