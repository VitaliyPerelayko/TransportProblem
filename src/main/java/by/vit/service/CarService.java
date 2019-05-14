package by.vit.service;

import by.vit.model.Car;
import by.vit.model.Point;

import java.util.List;

/**
 * Service layer for Car entity.
 */
public interface CarService {

    /**
     * Find all cars from database.
     *
     * @return list of cars
     */
    List<Car> findAll();

    /**
     * find all cars in given point
     *
     * @param point point, in which we want find all cars
     * @return list of car
     */
    List<Car> findAllByPoint(Point point);

    /**
     * Save new entity Car.
     *
     * @param car car entity
     * @return saved entity
     */
    Car save(Car car);

    /**
     * Update entity Car.
     *
     * @param car car entity
     * @return updated entity
     */
    Car update(Car car);

    /**
     * Retrieves a Car by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Car none found
     */
    Car findById(Long id);

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    Car getById(Long id);

    /**
     * Deletes a given entity.
     *
     * @param car car entity
     */
    void delete(Car car);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(Long id);
}
