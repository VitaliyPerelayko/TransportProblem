package by.vit.repository;

import by.vit.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for CarModels
 */
public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    /**
     * Return true, if carModel with this name exist in database,
     * and false otherwise.
     *
     * @param name
     * @return true or false
     */
    boolean existsByName(String name);

    /**
     * Find carModel by name from DataBase
     *
     * @param name of carModel
     * @return carModel
     */
    CarModel findByName(String name);
}
