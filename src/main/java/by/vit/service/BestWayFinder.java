package by.vit.service;

import by.vit.model.Car;
import by.vit.model.Point;

import java.util.List;
import java.util.Map;

/**
 * Interface for Service
 */
public interface BestWayFinder {
    Map<Car, List<String>> getSolution();

    void setConditions(Point[] points, Double[] order);
}
