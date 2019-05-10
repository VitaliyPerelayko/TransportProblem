package by.vit.service.transportproblemsolve;

import by.vit.model.Point;
import by.vit.model.Road;
import com.programmerare.shortestpaths.core.api.Path;

import java.util.List;

/**
 * Basic interface. It find shortest path between points.
 */
public interface DistanceMatrixFinder {

    /**
     * set arrays of points and roads for create distance matrix
     *
     * @param roads array of all roads from database
     * @param points array of points which
     *               First point is point from which deliver will start.
     *               The others it's delivery points.
     */
    void setConditions(Road[] roads, Point[] points);

    /**
     * get method
     * @return matrix of distance between points
     * example
     * pointId 1   5   8   13
     * 1     0    8   10   3
     * 5     8    0   4    7
     * 8     10   4   0    1
     * 13    3    7   1    0
     *
     */
    Double[][] getDistanceMatrix();

    /**
     * get method
     * @return List of detailed path between points
     * example
     * path from point 1 to point 5
     * 1 ->(weight 3) 4 ->(weight 2) 7 ->(weight 7) 5
     */
    List<Path> getPathList();

    /**
     * get method
     * @return Array of PointId.
     * First point it's point from deliver starts. The rest it's point of deliver.
     */
    Long[] getPointsId();
}
