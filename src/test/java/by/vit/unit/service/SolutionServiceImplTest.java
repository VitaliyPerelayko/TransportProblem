package by.vit.unit.service;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.*;
import by.vit.model.solution.Solution;
import by.vit.repository.RouteRepository;
import by.vit.repository.SolutionCarRepository;
import by.vit.repository.SolutionRepository;
import by.vit.service.CarService;
import by.vit.service.PointService;
import by.vit.service.RoadService;
import by.vit.service.UserService;
import by.vit.service.impl.SolutionServiceImpl;
import by.vit.service.transportproblemsolve.Conditions;
import by.vit.service.transportproblemsolve.DistanceMatrixFinder;
import by.vit.service.transportproblemsolve.SolverOfTP;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SolutionServiceImplTest {

    @InjectMocks
    private SolutionServiceImpl solutionService;

    @Mock
    private RoadService roadService;
    @Mock
    private CarService carService;
    @Mock
    private PointService pointService;
    @Mock
    private UserService userService;

    @Mock
    private SolutionRepository solutionRepository;
    @Mock
    private SolutionCarRepository solutionCarRepository;
    @Mock
    private RouteRepository routeRepository;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private DistanceMatrixFinder distanceMatrix;
    @Mock
    private SolverOfTP solver;
    @Mock
    private Conditions conditions;

    private String username = "Elvis";

    private Road[] roads = new Road[]{};

    private Point[] points = {getPoint(2L),
            getPoint(1L),
            getPoint(4L),
            getPoint(7L),
    };

    private CarModel[] carModels = {
            getCarModel(1L, "Renault", 50D, 150D),
            getCarModel(2L, "Renault", 20D, 150D),
            getCarModel(3L, "Renault", 30D, 150D)
    };

    private List<Car> cars = Arrays.asList(
            getCar(1L, carModels[0], 1.9),
            getCar(2L, carModels[1], 1.3),
            getCar(3L, carModels[2], 1.5)
    );

    private static RoadId getRoadId(Long p1, Long p2) {
        return new RoadId(p1, p2);
    }

    private static Road getRoad(RoadId roadId, Double distance) {
        Road road = new Road();
        road.setId(roadId);
        road.setDistance(distance);
        return road;
    }

    private static Point getPoint(Long id) {
        Point point = new Point();
        point.setId(id);
        return point;
    }

    private static CarModel getCarModel(Long id, String name, Double tonnage, Double space) {
        CarModel carModel = new CarModel(name, tonnage, space);
        carModel.setId(id);
        return carModel;
    }

    private static Car getCar(Long id, CarModel carModel, Double cost) {
        Car car = new Car();
        car.setId(id);
        car.setCarModel(carModel);
        car.setCost(cost);
        return car;
    }

    @Test
    public void testFindAll() {
        final List<Solution> solutionList = Collections.singletonList(new Solution());
        when(solutionRepository.findAll()).thenReturn(solutionList);
        assertEquals(solutionService.findAll(), solutionList);
    }

    @Test
    public void testFindById() {
        final Solution solution = new Solution();
        when(solutionRepository.findById(any(Long.class))).thenReturn(Optional.of(solution));
        assertEquals(solutionService.findById(1L), solution);
    }

    @Test
    public void testFindByIdException() {
        when(solutionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> solutionService.findById(1L));
    }

    @Test
    public void testDeleteById() {
        final Solution solution = new Solution();
        solution.setId(1L);
        doNothing().when(solutionRepository).deleteById(any(Long.class));
        when(solutionRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> solutionService.deleteById(1L));
    }

    @Test
    public void testSetConditions() {

        Double[] order = {20D, 25D, 15D};

        when(carService.findAllByPoint(points[0])).thenReturn(cars);
        when(roadService.findAll()).thenReturn(Arrays.asList(roads));
        doNothing().when(distanceMatrix).setConditions(any(),any());
        doNothing().when(conditions).setParam(any(),any(),anyBoolean(),any());

        assertDoesNotThrow(() -> solutionService.setConditions(points,order,username));
    }

    @Test
    public void testSetConditionsException() {

        Double[] order = {20D, 100D, 160D};

        when(carService.findAllByPoint(points[0])).thenReturn(cars);

        assertThrows(RuntimeException.class,() -> solutionService.setConditions(points,order,username));
    }
}
