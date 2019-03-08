package by.vit.dao.impl;

import by.vit.dao.RoadDAO;
import by.vit.model.Road;
import by.vit.model.compositekey.RoadId;

public class RoadDAOImpl extends GenericDAOImpl<Road, RoadId> implements RoadDAO {
    private volatile static RoadDAOImpl instance;

    private RoadDAOImpl() {
        super(Road.class);
    }

    public static RoadDAOImpl getInstance() {
        if (instance == null) {
            synchronized (RoadDAOImpl.class){
                if(instance==null){
                    instance = new RoadDAOImpl();
                }
            }
        }
        return instance;
    }
}
