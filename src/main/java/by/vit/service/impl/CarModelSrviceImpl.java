package by.vit.service.impl;

import by.vit.model.CarModel;
import by.vit.repository.CarModelRepository;
import by.vit.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for CarModel entity.
 */
@Service
public class CarModelSrviceImpl implements CarModelService {
    private CarModelRepository carModelRepository;

    public CarModelRepository getCarModelRepository() {
        return carModelRepository;
    }

    @Autowired
    public void setCarModelRepository(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    /**
     * Save new entity and update persist entity CarModel.
     *
     * @param carModel entity
     * @return saved or updated entity from database
     */
    @Override
    public CarModel saveCarModel(CarModel carModel) {
        CarModel savedEntity = getCarModelRepository().save(carModel);
        return savedEntity;
    }

    /**
     * Retrieves a CarModel by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if CarModel none found
     */
    @Override
    public CarModel findCarModel(Long id) throws Exception {
        Optional<CarModel> carModel = getCarModelRepository().findById(id);
        if (carModel.isPresent()) {
            return carModel.get();
        }
        throw new Exception("massage");
    }

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    @Override
    public CarModel getCarModel(Long id) {
        CarModel carModel = getCarModelRepository().getOne(id);
        return carModel;
    }

    /**
     * Deletes a given entity.
     *
     * @param carModel
     */
    @Override
    public void deleteCarModel(CarModel carModel) {
        getCarModelRepository().delete(carModel);
    }
}
