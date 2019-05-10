package by.vit.service;

import by.vit.model.Car;

import java.util.List;

public interface CarService {

    List<Car> findAll();

    Car save(Car car);

    Car update(Car car);

    Car findById(Long id);

    Car getById(Long id);

    void delete(Car car);

    void deleteById(Long id);
}
