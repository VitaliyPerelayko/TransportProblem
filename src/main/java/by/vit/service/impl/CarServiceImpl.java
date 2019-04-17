package by.vit.service.impl;

import by.vit.model.Car;
import by.vit.repository.CarRepository;
import by.vit.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of service layer for Car entity.
 */
@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;

    public CarRepository getCarRepository() {
        return carRepository;
    }

    @Autowired
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Save new entity and update persist entity Car.
     *
     * @param car entity
     * @return saved or updated entity from database
     */
    @Override
    public Car saveCar(Car car) {
        Car savedEntity = getCarRepository().save(car);
        return savedEntity;
    }

    /**
     * Retrieves a Car by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws Exception if Car none found
     */
    @Override
    public Car findCar(Long id) throws Exception {
        Optional<Car> car = getCarRepository().findById(id);
        if (car.isPresent()) {
            return car.get();
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
    public Car getCar(Long id) {
        Car car = getCarRepository().getOne(id);
        return car;
    }

    /**
     * Deletes a given entity.
     *
     * @param car
     */
    @Override
    public void deleteCar(Car car) {
        getCarRepository().delete(car);
    }
}
