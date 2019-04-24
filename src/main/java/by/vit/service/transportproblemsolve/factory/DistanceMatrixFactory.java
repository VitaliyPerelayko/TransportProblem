package by.vit.service.transportproblemsolve.factory;

import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.service.transportproblemsolve.DistanceMatrixFinder;
import by.vit.service.transportproblemsolve.impl.YanQiDistanceMatrixFinderImpl;

public class DistanceMatrixFactory {
    public enum Type {YanQi}



    public static DistanceMatrixFinder
    createDistanceMatrix(Type type, Road[] roads, Point[] points) {
        DistanceMatrixFinder distanceMatrixFinder = new YanQiDistanceMatrixFinderImpl(roads,points);
        return distanceMatrixFinder;
    }
}
