package by.vit.repository;

import by.vit.model.solution.Route;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Route
 */
public interface RouteRepository extends JpaRepository<Route, Long> {
}
