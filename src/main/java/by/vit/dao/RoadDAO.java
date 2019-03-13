package by.vit.dao;

import by.vit.model.Road;
import by.vit.model.RoadId;

import java.util.List;

public interface RoadDAO extends GenericDAO<Road, RoadId> {

    List<Road> findAll();
}
