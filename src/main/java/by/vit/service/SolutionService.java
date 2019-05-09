package by.vit.service;

import by.vit.model.Point;
import by.vit.model.solution.Solution;

import java.util.List;

/**
 * Interface for Service
 */
public interface SolutionService {

    Solution getAndSaveSolution();

    List<Solution> findAll();

    Solution findById(Long id);

    void deleteById(Long id);

    void setConditions(Point[] points, Double[] order, String username);
}
