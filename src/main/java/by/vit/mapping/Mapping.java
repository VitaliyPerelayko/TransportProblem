package by.vit.mapping;

import by.vit.dto.request.TaskDTO;
import by.vit.dto.response.solutiondto.SolutionDTO;
import by.vit.model.Point;
import by.vit.model.RoadId;
import by.vit.pojo.solution.Solution;

public interface Mapping {
    /**
     * maps the taskDTO to the array of points
     *
     * @param taskDTO it's list of points and list of orders (mass of cargo, that we need to deliver to the point)
     * @return
     */
    Point[] mapTaskToPoint(TaskDTO taskDTO);

    /**
     * maps the Solution to the SolutionDTO
     *
     * @param solution
     * @return
     */
    SolutionDTO mapSolution(Solution solution);

    RoadId mapToRoadId(Long id1, Long id2);
}
