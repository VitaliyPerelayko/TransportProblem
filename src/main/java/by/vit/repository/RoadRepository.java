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
    @Query("UPDATE Road road SET road.distance = :distance WHERE ((road.point1id = :id1 AND road.point2id = :id2) OR (road.point1id = :id2 AND road.point2id = :id1))")
    Road update(@Param("distance") Double distance, @Param("id1") Long id1, @Param("id2") Long id2);
}
