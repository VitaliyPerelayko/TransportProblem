package by.vit.repository;

import by.vit.model.solution.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Solution
 */
public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
