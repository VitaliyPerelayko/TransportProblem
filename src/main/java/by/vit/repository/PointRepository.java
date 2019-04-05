package by.vit.repository;

import by.vit.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Points
 */
public interface PointRepository extends JpaRepository<Point, Long> {
    /**
     * Get point by name from DataBase
     *
     * @param name of point
     * @return point
     */
    Point findByName(String name);
}
