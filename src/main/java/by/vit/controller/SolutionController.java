package by.vit.controller;

import by.vit.dto.PointDTO;
import by.vit.dto.request.TaskDTO;
import by.vit.dto.response.CarResponseDTO;
import by.vit.dto.response.solutiondto.RouteDTO;
import by.vit.dto.response.solutiondto.SolutionDTO;
import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.model.solution.Route;
import by.vit.model.solution.Solution;
import by.vit.service.SolutionService;
import by.vit.service.transportproblemsolve.factory.DistanceMatrixFactory;
import by.vit.service.transportproblemsolve.factory.SolverFactory;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/solution")
public class SolutionController {
    private final SolutionService solutionService;
    private final Mapper mapper;

    public SolutionController(SolutionService solutionService, Mapper mapper) {
        this.solutionService = solutionService;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SolutionDTO> save(@Valid @RequestBody TaskDTO taskDTO) {
        solutionService.setDistanceMatrixFinder(DistanceMatrixFactory.Type.YanQi);
        solutionService.setSolver(SolverFactory.Type.CLP);
        Point[] points = new Point[taskDTO.getPointDTOList().size()];
        points = taskDTO.getPointDTOList().stream().map(
                (pointDto) -> mapper.map(pointDto, Point.class)).collect(Collectors.toList()).toArray(points);
        Double[] orders = new Double[taskDTO.getOrderList().size()];
        orders = taskDTO.getOrderList().toArray(orders);

        solutionService.setConditions(points, orders);
        return new ResponseEntity<>(mapSolution(solutionService.getSolution()), HttpStatus.OK);
    }

    private SolutionDTO mapSolution(Solution solution) {
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
