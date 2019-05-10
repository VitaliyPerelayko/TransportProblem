package by.vit.service;

import by.vit.model.CarModel;

import java.util.List;

public interface CarModelService {

    List<CarModel> findAll();

    CarModel save(CarModel carModel);

    CarModel update(CarModel carModel);

    CarModel findById(Long id);

    CarModel getById(Long id);

    void delete(CarModel carModel);

    void deleteById(Long id);
}
