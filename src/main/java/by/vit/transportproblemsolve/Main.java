package by.vit.transportproblemsolve;


import com.programmerare.shortestpaths.adapter.yanqi.PathFinderFactoryYanQi;
import com.programmerare.shortestpaths.core.api.*;
import com.programmerare.shortestpaths.core.validation.GraphEdgesValidationDesired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.programmerare.shortestpaths.core.impl.EdgeImpl.createEdge;
import static com.programmerare.shortestpaths.core.impl.GraphImpl.createGraph;
import static com.programmerare.shortestpaths.core.impl.VertexImpl.createVertex;
import static com.programmerare.shortestpaths.core.impl.WeightImpl.createWeight;

public class Main {
    public static void main(String[] args) {
    Vertex a = createVertex("A");
    Vertex b = createVertex("B");
    Vertex c = createVertex("C");
    Vertex d = createVertex("D");

    List<Edge> edges = Arrays.asList(
            createEdge(a, b, createWeight(5)),
            createEdge(a, c, createWeight(6)),
            createEdge(b, c, createWeight(7)),
            createEdge(b, d, createWeight(8)),
            createEdge(c, d, createWeight(9))
    );

    Graph graph = createGraph(edges, GraphEdgesValidationDesired.YES);

    // the two first below implementations "YanQi" and "Bsmock" are available from OSSRH ("Maven Central")
    PathFinderFactory pathFinderFactory = new PathFinderFactoryYanQi(); // available from "Maven Central"
    //PathFinderFactory pathFinderFactory = new PathFinderFactoryBsmock(); // available from "Maven Central"
    // or: pathFinderFactory = new PathFinderFactoryJgrapht();  // currently NOT available from "Maven Central" !
    // or: pathFinderFactory = new PathFinderFactoryReneArgento(); // currently NOT available from "Maven Central" !
    // or: pathFinderFactory = new PathFinderFactoryMulavito(); // currently NOT available from "Maven Central" !
    // (currently there are five implementations)

    PathFinder pathFinder = pathFinderFactory.createPathFinder(graph);
    List<Path> shortestPaths = pathFinder.findShortestPaths(a, d, 10); // last parameter is max number to return but in this case there are only 3 possible paths
	for (Path path : shortestPaths) {
        Weight totalWeightForPath = path.getTotalWeightForPath();
        System.out.println(totalWeightForPath);
        List<Edge> pathEdges = path.getEdgesForPath();
        for (Edge edge : pathEdges) {
            Vertex startVertex = edge.getStartVertex();
            Vertex endVertex = edge.getEndVertex();
            Weight edgeWeight = edge.getEdgeWeight();
            System.out.println(startVertex);
            System.out.println(endVertex);
            System.out.println(edgeWeight);
        }
    }
}
}
