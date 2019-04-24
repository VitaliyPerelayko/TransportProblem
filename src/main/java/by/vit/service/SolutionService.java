package by.vit.service;

import by.vit.model.Point;
import by.vit.model.solution.Solution;
import by.vit.service.transportproblemsolve.factory.DistanceMatrixFactory;
import by.vit.service.transportproblemsolve.factory.SolverFactory;

/**
 * Interface for Service
 */
public interface SolutionService {

    Solution getSolution();

    void setConditions(Point[] points, Double[] order);

    void setSolver(SolverFactory.Type type);

    void setDistanceMatrixFinder(DistanceMatrixFactory.Type type);
}
