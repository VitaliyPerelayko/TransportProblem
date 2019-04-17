package by.vit.service;

import by.vit.model.CarModel;

public interface CarModelService {

    CarModel saveCarModel(CarModel carModel);

    CarModel findCarModel(Long id) throws Exception;

    CarModel getCarModel(Long id);

    void deleteCarModel(CarModel carModel);
}
