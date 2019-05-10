package by.vit.repository;

import by.vit.model.solution.SolutionCar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for SolutionCar
 */
public interface SolutionCarRepository extends JpaRepository<SolutionCar, Long> {
}
