package by.vit.dto.response.solutiondto;

import by.vit.dto.response.CarResponseDTO;

import java.util.List;
import java.util.Map;

public class SolutionDTO {
    private Map<CarResponseDTO,List<RouteDTO>> solution;

    public Map<CarResponseDTO,List<RouteDTO>> getSolution() {
        return solution;
    }

    public void setSolution(Map<CarResponseDTO, List<RouteDTO>> solution) {
        this.solution = solution;
    }
}
