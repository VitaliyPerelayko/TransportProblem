package by.vit.repository;

import by.vit.model.Road;
import by.vit.model.RoadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository layer for Cars
 */
public interface RoadRepository extends JpaRepository<Road, RoadId> {

    /**
     * Find all Roads from database
     *
     * @return List<Road>
     */
    List<Road> findAll();
}
