package by.vit.service;

import by.vit.model.Car;

public interface CarService {
    Car saveCar(Car car);

    Car findCar(Long id) throws Exception;

    Car getCar(Long id);

    void deleteCar(Car car);
}
