package by.vit.dao;

import by.vit.model.Car;

import java.util.List;

public interface CarDAO extends GenericDAO<Car,Long> {
    enum restriction{EQUAL,MORE,LESS,NO};
    List<Car> carModelByRestriction(restriction restrictionTonage, Double mass);
}
