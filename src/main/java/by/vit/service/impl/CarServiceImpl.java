package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.model.User;
import by.vit.repository.CarRepository;
import by.vit.service.CarModelService;
import by.vit.service.PointService;
import by.vit.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of service layer for Car entity.
 */
@Service
@Transactional
public class CarServiceImpl implements by.vit.service.CarService {

    private final LocalizedMessageSource localizedMessageSource;

    private final CarRepository carRepository;

    private final CarModelService carModelService;
    private final PointService pointService;
    private final UserService userService;

    public CarServiceImpl(LocalizedMessageSource localizedMessageSource, CarRepository carRepository,
                          CarModelService carModelService, PointService pointService,
                          UserService userService) {
        this.localizedMessageSource = localizedMessageSource;
        this.carRepository = carRepository;
        this.carModelService = carModelService;
        this.pointService = pointService;
        this.userService = userService;
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
     * find all cars in given point
     *
     * @param point point, in which we want find all cars
     * @return list of car
     */
    @Override
    public List<Car> findAllByPoint(Point point){
        return carRepository.findAllByPoint(point);
    }

    /**
     * Save new entity Car.
     *
     * @param car car entity
     * @return saved entity
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
     * @param car car entity
     * @return updated entity
     */
    @Override
    public Car update(Car car) {
        validate(car.getId() == null,
                localizedMessageSource.getMessage("error.car.haveNoId", new Object[]{}));
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
     * @param car car entity
     */
    @Override
    public void delete(Car car) {
        final Long id = car.getId();
        validate(id == null, localizedMessageSource.getMessage("error.car.haveNoId", new Object[]{}));
        isExist(id);
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
        isExist(id);
        carRepository.deleteById(id);
    }

    private Car saveAndFlush(Car car) {
        validate(car.getCarModel() == null || car.getCarModel().getId() == null,
                localizedMessageSource.getMessage("error.car.carModel.isNull", new Object[]{}));
        car.setCarModel(carModelService.findById(car.getCarModel().getId()));
        validate(car.getPoint() == null || car.getPoint().getId() == null,
                localizedMessageSource.getMessage("error.car.point.isNull", new Object[]{}));
        car.setPoint(pointService.findById(car.getPoint().getId()));

        User transporter = car.getTransporter();
        validate(transporter == null || transporter.getId() == null,
                localizedMessageSource.getMessage("error.car.transporter.isNull", new Object[]{}));
        validate(transporter.getRoles().stream().noneMatch(role ->
                        role.getName().equals("ROLE_TRANSPORTER")),
                localizedMessageSource.getMessage("error.car.user.role.noTransporter", new Object[]{}));
        car.setTransporter(userService.findById(car.getTransporter().getId()));
        return carRepository.saveAndFlush(car);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }

    private void isExist(Long id){
        validate(!carRepository.existsById(id),
                localizedMessageSource.getMessage("error.car.id.notExist", new Object[]{}));
    }
}
