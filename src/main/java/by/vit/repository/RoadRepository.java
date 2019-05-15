package by.vit.repository;

import by.vit.model.Road;
import by.vit.model.RoadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository layer for Road
 */
public interface RoadRepository extends JpaRepository<Road, RoadId> {
}
