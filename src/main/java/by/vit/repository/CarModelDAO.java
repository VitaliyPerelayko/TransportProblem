package by.vit.repository;

import by.vit.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelDAO extends JpaRepository<CarModel, Long> {
}
