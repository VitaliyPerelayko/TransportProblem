package by.vit.repository;

import by.vit.model.Road;
import by.vit.model.RoadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadDAO extends JpaRepository<Road, RoadId> {

    List<Road> findAll();
}
