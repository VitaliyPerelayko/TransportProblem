package by.vit.service;

import by.vit.model.Point;

import java.util.Map;

public interface BestWayFinder {
    Map<Long, Map<Long, Double>> getSolution();

    void setConditions(Point[] points,Double[] order);
}
