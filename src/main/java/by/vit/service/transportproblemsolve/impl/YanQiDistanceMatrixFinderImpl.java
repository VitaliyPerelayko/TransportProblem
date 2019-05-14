package by.vit.service.transportproblemsolve.impl;


import by.vit.component.LocalizedMessageSource;
import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.service.transportproblemsolve.DistanceMatrixFinder;
import com.programmerare.shortestpaths.adapter.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.*;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

/**
 * Implementation DistanceMatrixFinder. It uses Yen's Ranking LoopLess Paths Algorithm
 */
@Service
public class YanQiDistanceMatrixFinderImpl implements DistanceMatrixFinder {
    private static final PathFinderFactory pathFinderFactory = new PathFinderFactoryYanQi();
    private final LocalizedMessageSource localizedMessageSource;


    private Double[][] distanceMatrix;
    private List<Path> pathList;
    private Long[] pointsId;

    public YanQiDistanceMatrixFinderImpl(LocalizedMessageSource localizedMessageSource) {
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * set arrays of points and roads for create distance matrix
     *
     * @param roads array of all roads from database
     * @param points array of points which
     *               First point is point from which deliver will start.
     */
    @Override
    public void setConditions(Road[] roads, Point[] points) {
        this.distanceMatrix = new Double[points.length + 1][points.length + 1];
        this.pathList = new ArrayList();
        createDistanceMatrix(createGraphFromRoads(roads), createArrayOfPointId(points));
        pointsId = new Long[points.length - 1];
        for (int i = 0; i < pointsId.length; i++) {
            pointsId[i] = points[i + 1].getId();
        }
    }

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
    @Override
    public Double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    /**
     * get method
     * @return List of detailed path between points
     * example
     * path from point 1 to point 5
     * 1 ->(weight 3) 4 ->(weight 2) 7 ->(weight 7) 5
     */
    @Override
    public List<Path> getPathList() {
        return pathList;
    }

    /**
     * get method
     * @return Array of PointId.
     * First point it's point from deliver starts. The rest it's point of deliver.
     */
    @Override
    public Long[] getPointsId() {
        return pointsId;
    }

    private Graph createGraphFromRoads(Road[] roads) {
        List<Edge> edges = new ArrayList<>();
        for (Road road : roads) {
            Vertex vertex1 = createVertex(road.getId().getPoint1Id().toString());
            Vertex vertex2 = createVertex(road.getId().getPoint2Id().toString());
            edges.add(createEdge(vertex1, vertex2, createWeight(road.getDistance())));
            edges.add(createEdge(vertex2, vertex1, createWeight(road.getDistance())));
        }
        return createGraph(edges, GraphEdgesValidationDesired.YES);
    }

    private Long[] createArrayOfPointId(Point[] points) {
        Long[] pointsId = new Long[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsId[i] = points[i].getId();
        }
        return pointsId;
    }

    private Vertex getVertexById(Graph graph, Long id) {

        for (Vertex vertex : graph.getVertices()) {
            if (vertex.getVertexId().equals(id.toString())) {
                return vertex;
            }
        }
        throw new RuntimeException(
                localizedMessageSource.getMessage("point.hasNo.roads", new Object[]{id}));
    }

    private void createDistanceMatrix(Graph graph, Long[] pointsId) {
        PathFinder pathFinder = pathFinderFactory.createPathFinder(graph);
        int pointIdLength = pointsId.length;
        for (int i = 0; i < pointIdLength; i++) {
            this.distanceMatrix[i][i] = 0D;
            this.distanceMatrix[0][i + 1] = pointsId[i].doubleValue();
            this.distanceMatrix[i + 1][0] = pointsId[i].doubleValue();

            for (int j = i + 1; j < pointIdLength; j++) {
                Vertex vertex1 = getVertexById(graph, pointsId[i]);
                Vertex vertex2 = getVertexById(graph, pointsId[j]);
                List<Path> paths = pathFinder.findShortestPaths(vertex1, vertex2, 1);
                Path path = paths.get(0);
                this.pathList.add(path);
                this.distanceMatrix[i + 1][j + 1] = path.getTotalWeightForPath().getWeightValue();
                this.distanceMatrix[j + 1][i + 1] = this.distanceMatrix[i + 1][j + 1];
            }
        }
        this.distanceMatrix[pointIdLength][pointIdLength] = 0D;
    }
}
