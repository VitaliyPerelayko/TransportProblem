package by.vit.service;

import by.vit.model.CarModel;

import java.util.List;

/**
 * Service layer for CarModel entity.
 */
public interface CarModelService {

    /**
     * Find all carModels from database.
     *
     * @return List<CarModel>
     */
    List<CarModel> findAll();

    /**
     * Save new entity CarModel.
     *
     * @param carModel entity
     * @return saved entity from database
     */
    CarModel save(CarModel carModel);

    /**
     * Update entity CarModel.
     *
     * @param carModel entity
     * @return updated entity
     */
    CarModel update(CarModel carModel);

    /**
     * Retrieves a CarModel by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if CarModel none found
     */
    CarModel findById(Long id);

    /**
     * Get an instance, whose state may be lazily fetched.
     * If the requested instance does not exist in the database,
     * the <code>EntityNotFoundException</code> is thrown when the instance
     * state is first accessed.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    CarModel getById(Long id);

    /**
     * Deletes a given entity.
     *
     * @param carModel
     */
    void delete(CarModel carModel);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(Long id);
}
