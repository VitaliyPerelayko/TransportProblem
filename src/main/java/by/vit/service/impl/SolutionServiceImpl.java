package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.model.solution.Route;
import by.vit.model.solution.Solution;
import by.vit.model.solution.SolutionCar;
import by.vit.repository.RouteRepository;
import by.vit.repository.SolutionCarRepository;
import by.vit.repository.SolutionRepository;
import by.vit.service.*;
import by.vit.service.transportproblemsolve.Conditions;
import by.vit.service.transportproblemsolve.DistanceMatrixFinder;
import by.vit.service.transportproblemsolve.SolverOfTP;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementation of service layer for Solution entity.
 * Service finds the best solution to the task:
 * If we have many cars at the same point,
 * which car will go to which point and
 * how much cargo will it deliver?
 */
@Service
public class SolutionServiceImpl implements SolutionService {
    private final RoadService roadService;
    private final CarService carService;
    private final PointService pointService;
    private final UserService userService;

    private final SolutionRepository solutionRepository;
    private final SolutionCarRepository solutionCarRepository;
    private final RouteRepository routeRepository;

    private final LocalizedMessageSource localizedMessageSource;

    private final DistanceMatrixFinder distanceMatrix;
    private final Conditions conditions;
    private final SolverOfTP solver;

    /**
     * User who has asked for solution
     */
    private String username;

    public SolutionServiceImpl(RoadService roadService, CarService carService, PointService pointService,
                               UserService userService, SolutionRepository solutionRepository,
                               SolutionCarRepository solutionCarRepository,
                               RouteRepository routeRepository, LocalizedMessageSource localizedMessageSource,
                               DistanceMatrixFinder distanceMatrixFinder, Conditions conditions, SolverOfTP solver) {
        this.roadService = roadService;
        this.carService = carService;
        this.pointService = pointService;
        this.userService = userService;
        this.solutionRepository = solutionRepository;
        this.solutionCarRepository = solutionCarRepository;
        this.routeRepository = routeRepository;
        this.localizedMessageSource = localizedMessageSource;
        this.distanceMatrix = distanceMatrixFinder;
        this.conditions = conditions;
        this.solver = solver;
    }

    /**
     * Solve the transport problem and return solution.
     * Save to database Solution, SolutionCars and Routes
     *
     * @return solution
     */
    @Override
    public Solution getAndSaveSolution() {
        Map<Long, List<String>> solutionInterpretation = solver.getInterpretation();
        Solution solution = new Solution();
        solution.setDateTime(LocalDateTime.now());
        solution.setSupplier(userService.findByUsername(username));
        final Solution savedSolution = solutionRepository.saveAndFlush(solution);

        solutionInterpretation.keySet().forEach((i) -> {
            final Car car = carService.findById(i);
            final SolutionCar solutionCar = new SolutionCar();
            solutionCar.setSolution(solution);
            solutionCar.setCar(car);
            final SolutionCar savedSolutionCar = solutionCarRepository.saveAndFlush(solutionCar);
            final List<String> routes = solutionInterpretation.get(i);

            for (int j = 0; j < routes.size() / 3; j++) {
                final Route route = new Route();
                route.setSolutionCar(savedSolutionCar);
                route.setPoint(pointService.findById(Long.
                        parseLong(routes.get(j * 3))));
                route.setMass(Double.parseDouble(routes.get(j * 3 + 1)));
                route.setCost(Double.parseDouble(routes.get(j * 3 + 2)));
                routeRepository.saveAndFlush(route);
            }
        });
        return savedSolution;
    }

    /**
     * Find all solution from database.
     *
     * @return List<Role>
     */
    @Override
    public List<Solution> findAll() {
        return solutionRepository.findAll();
    }

    /**
     * Retrieves a Solution by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws RuntimeException if Solution none found
     */
    @Override
    public Solution findById(Long id) {
        Optional<Solution> solution = solutionRepository.findById(id);
        validate(!solution.isPresent(),
                localizedMessageSource.getMessage("error.solution.id.notExist", new Object[]{}));
        return solution.get();
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    @Override
    public void deleteById(Long id) {
        isExist(id);
        solutionRepository.deleteById(id);
    }

    /**
     * Set condition for solution finder
     *
     * @param points   List of points. First point is point from which deliver will start.
     *                 The others it's delivery points.
     * @param order    Mass of cargo that we need to deliver
     * @param username User who has asked for solution
     */
    @Override
    public void setConditions(Point[] points, Double[] order, String username) {
        this.username = username;
        List<Car> cars = carService.findAllByPoint(points[0]);
        Double difference = validOrder(cars, order);
        boolean flag = difference != 0;
        final List<Road> roadList = roadService.findAll();
        final Road[] roads = new Road[roadList.size()];
        distanceMatrix.setConditions(roadList.toArray(roads), points);
        conditions.setParam(cars, order, flag, difference);
    }

    private Double validOrder(List<Car> cars, Double[] order) {
        Double carSumTonnage = cars.stream().mapToDouble(car -> car.getCarModel().getTonnage()).sum();
        Double sumOfOrder = Arrays.stream(order).reduce((a, b) -> a + b).get();
        if (carSumTonnage - sumOfOrder < 0) {
            throw new RuntimeException(
                    localizedMessageSource.getMessage("error.solution.notEnough.cars", new Object[]{}));
        }
        return carSumTonnage - sumOfOrder;
    }

    private void isExist(Long id) {
        validate(!solutionRepository.existsById(id),
                localizedMessageSource.getMessage("error.solution.id.notExist", new Object[]{}));
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
