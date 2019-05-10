package by.vit.service;

import by.vit.model.Road;
import by.vit.model.RoadId;

import java.util.List;

public interface RoadService {

    List<Road> findAll();

    Road save(Road road);

    Road update(Road road);

    Road findById(RoadId id);

    Road getById(RoadId id);

    void delete(Road road);

    void deleteById(RoadId id);
}
