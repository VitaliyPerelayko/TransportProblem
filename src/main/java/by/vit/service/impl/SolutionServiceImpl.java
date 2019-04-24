package by.vit.service.impl;

import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.model.solution.Solution;
import by.vit.repository.CarRepository;
import by.vit.repository.PointRepository;
import by.vit.repository.RoadRepository;
import by.vit.service.SolutionService;
import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.DistanceMatrixFinder;
import by.vit.service.transportproblemsolve.SolverOfTP;
import by.vit.service.transportproblemsolve.factory.DistanceMatrixFactory;
import by.vit.service.transportproblemsolve.factory.SolverFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service find best solution of task: To which point will the car go and how much cargo will it deliver?
 * If we have many cars at the same point.
 */
@Service
public class SolutionServiceImpl implements SolutionService {
    private final RoadRepository roadRepository;
    private final CarRepository carRepository;
    private final PointRepository pointRepository;
    /**
     * Point of deliver
     */
    private Point[] points;
    /**
     * How much cargo we must deliver
     */
    private Double[] order;
    /**
     * Method of working out the distance
     */
    private DistanceMatrixFinder distanceMatrixFinder;
    private SolverOfTP solver;

    public SolutionServiceImpl(RoadRepository roadRepository, CarRepository carRepository, PointRepository pointRepository) {
        this.roadRepository = roadRepository;
        this.carRepository = carRepository;
        this.pointRepository = pointRepository;
    }

    /**
     * Set method of finding the shortest path
     *
     * @param type of algorithm
     */
    @Override
    public void setDistanceMatrixFinder(DistanceMatrixFactory.Type type) {
        this.distanceMatrixFinder = DistanceMatrixFactory.
                createDistanceMatrix(type, (Road[]) roadRepository.findAll().toArray(), points);
    }

    /**
     * Set method of solving linear programming problems
     *
     * @param type of algorithm
     */
    @Override
    public void setSolver(SolverFactory.Type type) {
        List<Car> cars = new ArrayList(points[0].getCars());
        ConditionTP conditionTP = new ConditionTP(distanceMatrixFinder, cars, order);
        this.solver = SolverFactory.createSolver(type, conditionTP);
    }

    /**
     * @return solution
     */
    @Override
    public Solution getSolution() {
        Map<Long, List<String>> solution = solver.getInterpretation();
        Solution solution1 = new Solution();
        for (Long i : solution.keySet()) {
            Car car = carRepository.findById(i).get();
            List<String> routes = solution.get(i);
            solution1.putCarRoute(car, getPointsFromSolution(routes),
                    getMassesFromSolution(routes), getCostsFromSolution(routes));
        }
        return solution1;
    }

    /**
     * Set condition for solution finder
     *
     * @param points Point of deliver
     * @param order  How much cargo we must deliver
     */
    @Override
    public void setConditions(Point[] points, Double[] order) {
        this.points = points;
        this.order = order;
    }


    private List<Point> getPointsFromSolution(List<String> routes) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < routes.size() / 3; i++) {
            Point point = pointRepository.findById(Long.
                    parseLong(routes.get(i * 3))).get();
            points.add(point);
        }
        return points;
    }

    private List<Double> getMassesFromSolution(List<String> routes) {
        List<Double> masses = new ArrayList<>();
        for (int i = 0; i < routes.size() / 3; i++) {
            Double mass = Double.parseDouble(routes.get(i * 3 + 1));
            masses.add(mass);
        }
        return masses;
    }

    private List<BigDecimal> getCostsFromSolution(List<String> routes) {
        List<BigDecimal> costs = new ArrayList<>();
        for (int i = 0; i < routes.size() / 3; i++) {
            BigDecimal cost = new BigDecimal(routes.get(i * 3 + 2));
            costs.add(cost);
        }
        return costs;
    }
}
