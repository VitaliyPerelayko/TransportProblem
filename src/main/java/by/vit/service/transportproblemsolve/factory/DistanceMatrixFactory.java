package by.vit.service.transportproblemsolve.factory;

import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.service.transportproblemsolve.DistanceMatrix;
import by.vit.service.transportproblemsolve.impl.YanQiDistanceMatrixImpl;

public class DistanceMatrixFactory {
    public enum Type {YanQi}



    public static DistanceMatrix createDistanseMatrix(Type type, Road[] roads, Point[] points){
        DistanceMatrix distanceMatrix = new YanQiDistanceMatrixImpl(roads,points);
        return distanceMatrix;
    }
}
