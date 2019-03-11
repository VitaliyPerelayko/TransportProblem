package by.vit.dao;

import by.vit.model.Point;

public interface PointDAO extends GenericDAO<Point,Long> {
    /**
     * Get point by name from DataBase
     * @param name of point
     * @return point
     */
    Point getPointByName(String name);
}
