package by.vit.service;

import by.vit.model.Road;
import by.vit.model.RoadId;

public interface RoadService {
    Road saveRoad(Road road);

    Road findRoad(RoadId id) throws Exception;

    Road getRoad(RoadId id);

    void deleteRoad(Road road);
}
