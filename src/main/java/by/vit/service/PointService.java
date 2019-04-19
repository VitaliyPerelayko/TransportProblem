package by.vit.service;

import by.vit.model.Point;

public interface PointService {

    Point save(Point point);

    Point update(Point point);

    Point findPoint(Long id) throws Exception;

    Point getPoint(Long id);

    void deletePoint(Point point);
}
