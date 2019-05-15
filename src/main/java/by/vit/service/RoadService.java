package by.vit.service;

import by.vit.model.Road;
import by.vit.model.RoadId;

import java.util.List;

/**
 * Service layer for Car entity.
 */
public interface RoadService {

    /**
     * Find all roads from database.
     *
     * @return List<Road>
     */
    List<Road> findAll();

    /**
     * Save new entity Road it.
     *
     * @param road entity
     * @return saved entity from database
     */
    Road save(Road road);

    /**
     * Update entity Road.
     *
     * @param road entity
     * @return updated entity from database
     */
    Road update(Road road);

    /**
     * Retrieves a Road by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Road none found
     */
    Road findById(RoadId id);

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    Road getById(RoadId id);

    /**
     * Deletes a given entity.
     *
     * @param road road entity
     */
    void delete(Road road);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(RoadId id);

    boolean isExistById(RoadId id);
}
