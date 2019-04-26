package by.vit.mapping.impl;

import by.vit.dto.PointDTO;
import by.vit.dto.request.TaskDTO;
import by.vit.dto.response.CarResponseDTO;
import by.vit.dto.response.solutiondto.RouteDTO;
import by.vit.dto.response.solutiondto.SolutionDTO;
import by.vit.mapping.Mapping;
import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.pojo.solution.Route;
import by.vit.pojo.solution.Solution;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Custom mapping service
 */
@Service
public class MappingImpl implements Mapping {
    private final Mapper mapper;

    public MappingImpl(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * maps the taskDTO to the array of points
     *
     * @param taskDTO it's list of points and list of orders (mass of cargo, that we need to deliver to the point)
     * @return
     */
    @Override
    public Point[] mapTaskToPoint(TaskDTO taskDTO) {
        Point[] points = new Point[taskDTO.getPointDTOList().size()];
        return taskDTO.getPointDTOList().stream().map(
                (pointDto) -> mapper.map(pointDto, Point.class)).collect(Collectors.toList()).toArray(points);
    }

    /**
     * maps the Solution to the SolutionDTO
     *
     * @param solution
     * @return
     */
    @Override
    public SolutionDTO mapSolution(Solution solution) {
        SolutionDTO solutionDTO = new SolutionDTO();
        Map<CarResponseDTO, List<RouteDTO>> map = new TreeMap<>();
        for (Car car : solution.getSolution().keySet()) {
            CarResponseDTO carResponseDTO = mapper.map(car, CarResponseDTO.class);
            List<RouteDTO> routeDTOList = new ArrayList<>();
            List<Route> routeList = solution.getSolution().get(car);
            for (Route route : routeList) {
                RouteDTO routeDTO = new RouteDTO();
                PointDTO pointDTO = mapper.map(route.getPointFinish(), PointDTO.class);
                routeDTO.setPointFinish(pointDTO);
                routeDTO.setMass(route.getMass());
                routeDTO.setCost(route.getCost());
                routeDTOList.add(routeDTO);
            }
            map.put(carResponseDTO, routeDTOList);
        }
        solutionDTO.setSolution(map);
        return solutionDTO;
    }
}
