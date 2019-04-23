package by.vit.repository;

import by.vit.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for CarModels
 */
public interface CarModelRepository extends JpaRepository<CarModel, Long> {
}
