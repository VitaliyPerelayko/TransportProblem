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

    /**
     * Update road from database
     *
     * @param distance new distance
     * @param id1 point1 id
     * @param id2 point2 id
     * @return
     */
    @Query("UPDATE Road road SET road.distance = :distance WHERE (road.id = :id1) OR (road.id = :id2)")
    Road update(@Param("distance") Double distance, @Param("id1") RoadId id1, @Param("id2") RoadId id2);
}
