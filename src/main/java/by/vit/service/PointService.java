package by.vit.service;

import by.vit.model.Point;

import java.util.List;

public interface PointService {

    List<Point> findAll();

    Point save(Point point);

    Point update(Point point);

    Point findById(Long id);

    Point getById(Long id);

    void delete(Point point);

    void deleteById(Long id);
}
