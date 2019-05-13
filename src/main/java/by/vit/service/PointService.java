package by.vit.service;

import by.vit.model.Point;

import java.util.List;

/**
 * service layer for Point entity
 */
public interface PointService {

    /**
     * Find all points from database.
     *
     * @return List of Point
     */
    List<Point> findAll();

    /**
     * Save new entity Point.
     *
     * @param point point entity
     * @return saved entity
     */
    Point save(Point point);

    /**
     * Update entity Point.
     *
     * @param point point entity
     * @return updated entity
     */
    Point update(Point point);

    /**
     * Retrieves a Point by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Point none found
     */
    Point findById(Long id);

    /**
     * Retrieves a Point by its name.
     *
     * @param name must not be {@literal null}.
     * @return the entity with the given name
     * @throws RuntimeException if Point none found
     */
    Point findByName(String name);

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    Point getById(Long id);

    /**
     * Deletes a given entity.
     *
     * @param point point entity
     */
    void delete(Point point);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(Long id);
}
