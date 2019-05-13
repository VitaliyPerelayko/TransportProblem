package by.vit.service;

import by.vit.model.Point;
import by.vit.model.solution.Solution;

import java.util.List;

/**
 * Service layer for Solution entity.
 * Service finds the best solution to the task:
 * If we have many cars at the same point,
 * which car will go to which point and
 * how much cargo will it deliver?
 */
public interface SolutionService {

    /**
     * Solve the transport problem and return solution.
     * Save to database Solution, SolutionCars and Routes
     *
     * @return solution
     */
    Solution getAndSaveSolution();

    /**
     * Find all solution from database.
     *
     * @return List<Role>
     */
    List<Solution> findAll();

    /**
     * Retrieves a Solution by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Solution none found
     */
    Solution findById(Long id);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(Long id);

    /**
     * Set condition for solution finder
     *
     * @param points   List of points. First point is point from which deliver will start.
     *                 The others it's delivery points.
     * @param order    Mass of cargo that we need to deliver
     * @param username User who has asked for solution
     */
    void setConditions(Point[] points, Double[] order, String username);
}
