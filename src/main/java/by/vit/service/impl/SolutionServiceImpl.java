package by.vit.service.impl;

import by.vit.component.LocalizedMessageSource;
import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.model.solution.Route;
import by.vit.model.solution.Solution;
import by.vit.model.solution.SolutionCar;
import by.vit.repository.*;
import by.vit.service.SolutionService;
import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.DistanceMatrixFinder;
import by.vit.service.transportproblemsolve.SolverOfTP;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service find best solution of task: To which point will the car go and how much cargo will it deliver?
 * If we have many cars at the same point.
 */
@Service
public class SolutionServiceImpl implements SolutionService {
    private final RoadRepository roadRepository;
    private final CarRepository carRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final SolutionRepository solutionRepository;
    private final SolutionCarRepository solutionCarRepository;
    private final RouteRepository routeRepository;
    private final LocalizedMessageSource localizedMessageSource;
    /**
     * Method of working out the distance
     */
    private final DistanceMatrixFinder distanceMatrix;
    private final SolverOfTP solver;
    /**
     * Point of deliver
     */
    private Point[] points;
    /**
     * How much cargo we must deliver
     */
    private Double[] order;
    /**
     * User who has asked for solution
     */
    private String username;

    public SolutionServiceImpl(RoadRepository roadRepository, CarRepository carRepository,
                               PointRepository pointRepository, UserRepository userRepository,
                               SolutionRepository solutionRepository, SolutionCarRepository solutionCarRepository,
                               RouteRepository routeRepository, LocalizedMessageSource localizedMessageSource,
                               DistanceMatrixFinder distanceMatrixFinder, SolverOfTP solver) {
        this.roadRepository = roadRepository;
        this.carRepository = carRepository;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.solutionRepository = solutionRepository;
        this.solutionCarRepository = solutionCarRepository;
        this.routeRepository = routeRepository;
        this.localizedMessageSource = localizedMessageSource;
        this.distanceMatrix = distanceMatrixFinder;
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
        solution.setSupplier(userRepository.findByUsername(username));
        final Solution savedSolution = solutionRepository.save(solution);

        solutionInterpretation.keySet().forEach((i) -> {
            final Car car = carRepository.findById(i).get();
            final SolutionCar solutionCar = new SolutionCar();
            solutionCar.setSolution(solution);
            solutionCar.setCar(car);
            final SolutionCar savedSolutionCar = solutionCarRepository.save(solutionCar);
            final List<String> routes = solutionInterpretation.get(i);

            for (int j = 0; j < routes.size() / 3; j++) {
                final Route route = new Route();
                route.setSolutionCar(savedSolutionCar);
                route.setPoint(pointRepository.findById(Long.
                        parseLong(routes.get(j * 3))).get());
                route.setMass(Double.parseDouble(routes.get(j * 3 + 1)));
                route.setCost(Double.parseDouble(routes.get(j * 3 + 2)));
                routeRepository.save(route);
            }
        });
        return savedSolution;
    }

    /**
     * Find all roles from database.
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
        if (!solution.isPresent()) {
            throw new RuntimeException(
                    localizedMessageSource.getMessage("error.solution.id.notExist", new Object[]{})
            );
        }
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
        findById(id);
        solutionRepository.deleteById(id);
    }

    /**
     * Set condition for solution finder
     *
     * @param points   Point of deliver
     * @param order    Mass of cargo that we need to deliver
     * @param username User who has asked for solution
     */
    @Override
    public void setConditions(Point[] points, Double[] order, String username) {
        this.points = points;
        this.order = order;
        this.username = username;
        List<Car> cars = new ArrayList(points[0].getCars());
        Double difference = validOrder(cars, order);
        boolean flag = difference == 0 ? false : true;

        distanceMatrix.setConditions((Road[]) roadRepository.findAll().toArray(), points);
        ConditionTP conditionTP = new ConditionTP(distanceMatrix, cars, order, flag, difference);
        solver.setConditions(conditionTP);

    }

    private Double validOrder(List<Car> cars, Double[] order) {
        Double carSumTonnage = cars.stream().mapToDouble(car -> car.getCarModel().getTonnage()).sum();
        Double sumOfOrder = Arrays.stream(order).reduce((a, b) -> a + b).get();
        if (carSumTonnage - sumOfOrder < 0){
            throw new RuntimeException(
                    localizedMessageSource.getMessage("error.solution.notEnough.cars",new Object[]{}));
        }
        return carSumTonnage - sumOfOrder;
    }
}
