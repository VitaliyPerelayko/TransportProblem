package by.vit.repository;

import by.vit.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Points
 */
public interface PointRepository extends JpaRepository<Point, Long> {

    /**
     * Return true, if point with this name exist in database,
     * and false otherwise.
     *
     * @param name
     * @return true or false
     */
    boolean existsByName(String name);

    /**
     * Find point by name from DataBase
     *
     * @param name of point
     * @return point
     */
    Point findByName(String name);
}
