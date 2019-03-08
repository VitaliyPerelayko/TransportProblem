package by.vit.dao;

import by.vit.model.Point;

public interface PointDAO extends GenericDAO<Point,Long> {
    Point getPointByName(String name);
}
