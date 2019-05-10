package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Car;
import by.vit.model.User;
import by.vit.repository.CarModelRepository;
import by.vit.repository.CarRepository;
import by.vit.repository.PointRepository;
import by.vit.repository.UserRepository;
import by.vit.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of service layer for Car entity.
 */
@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final LocalizedMessageSource localizedMessageSource;

    private final CarRepository carRepository;
    private final CarModelRepository carModelRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public CarServiceImpl(LocalizedMessageSource localizedMessageSource, CarRepository carRepository,
                          CarModelRepository carModelRepository, PointRepository pointRepository,
                          UserRepository userRepository) {
        this.localizedMessageSource = localizedMessageSource;
        this.carRepository = carRepository;
        this.carModelRepository = carModelRepository;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find all cars from database.
     *
     * @return List<Car>
     */
    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    /**
     * Save new entity Car.
     *
     * @param car entity
     * @return saved entity from database
     */
    @Override
    public Car save(Car car) {
        validate(car.getId() != null,
                localizedMessageSource.getMessage("error.car.haveId", new Object[]{}));
        return saveAndFlush(car);
    }

    /**
     * Update entity Car.
     *
     * @param car entity
     * @return updated entity from database
     */
    @Override
    public Car update(Car car) {
        validate(car.getId() == null, localizedMessageSource.getMessage("error.car.haveNoId", new Object[]{}));
        return saveAndFlush(car);
    }


    /**
     * Retrieves a Car by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Car none found
     */
    @Override
    public Car findById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        validate(!(car.isPresent()), localizedMessageSource.getMessage("error.car.id.notExist", new Object[]{}));
        return car.get();
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
    public Car getById(Long id) {
        Car car = carRepository.getOne(id);
        return car;
    }

    /**
     * Deletes a given entity.
     *
     * @param car
     */
    @Override
    public void delete(Car car) {
        final Long id = car.getId();
        validate(id == null, localizedMessageSource.getMessage("error.car.haveNoId", new Object[]{}));
        findById(id);
        carRepository.delete(car);
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void deleteById(Long id) {
        findById(id);
        carRepository.deleteById(id);
    }

    private Car saveAndFlush(Car car){
        validate(car.getCarModel() == null || car.getCarModel().getId() == null,
                localizedMessageSource.getMessage("error.car.carModel.isNull", new Object[]{}));
        car.setCarModel(carModelRepository.getOne(car.getCarModel().getId()));
        validate(car.getPoint() == null || car.getPoint().getId() == null,
                localizedMessageSource.getMessage("error.car.point.isNull", new Object[]{}));
        car.setPoint(pointRepository.getOne(car.getPoint().getId()));

        User transporter= car.getTransporter();
        validate(transporter == null || transporter.getId() == null,
                localizedMessageSource.getMessage("error.car.transporter.isNull", new Object[]{}));
        validate(!transporter.getRoles().stream().filter(role ->
                role.getName().equals("TRANSPORTER")).findAny().isPresent(),
                localizedMessageSource.getMessage("error.car.user.role.noTransporter", new Object[]{}));
        car.setTransporter(userRepository.getOne(car.getTransporter().getId()));
        return carRepository.saveAndFlush(car);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
