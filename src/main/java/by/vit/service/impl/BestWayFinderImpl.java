package by.vit.service.impl;

import by.vit.model.Car;
import by.vit.model.Point;
import by.vit.model.Road;
import by.vit.repository.CarRepository;
import by.vit.repository.PointRepository;
import by.vit.repository.RoadRepository;
import by.vit.service.BestWayFinder;
import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.DistanceMatrix;
import by.vit.service.transportproblemsolve.SolverOfTP;
import by.vit.service.transportproblemsolve.factory.DistanceMatrixFactory;
import by.vit.service.transportproblemsolve.factory.SolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Service find best solution of task: To which point will the car go and how much cargo will it deliver?
 * If we have many cars at the same point.
 */
@Service
public class BestWayFinderImpl implements BestWayFinder {

    /**
     * Point of deliver
     */
    private Point[] points;
    /**
     * How much cargo we must deliver
     */
    private Double[] order;

    private RoadRepository roadRepository;
    private CarRepository carRepository;
    private PointRepository pointRepository;

    /**
     * Method of working out the distance
     */
    private DistanceMatrix distanceMatrix;
    private SolverOfTP solver;

    @Autowired
    public void setRoadRepository(RoadRepository roadRepository) {
        this.roadRepository = roadRepository;
    }

    @Autowired
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Autowired
    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void setDistanceMatrix(DistanceMatrixFactory.Type type) {
        this.distanceMatrix = DistanceMatrixFactory.
                createDistanseMatrix(type, (Road[]) roadRepository.findAll().toArray(), points);
    }

    public void setSolver(SolverFactory.Type type) {
        List<Car> cars = new ArrayList(points[0].getCars());
        ConditionTP conditionTP = new ConditionTP(distanceMatrix, cars, order);
        this.solver = SolverFactory.createSolver(type, conditionTP);
    }

    /**
     * method treats the solution before posting on view
     * @return info of solution: "car" will go to the "point" and will deliver "mass" of cargo
     */
    @Override
    public Map<Car, List<String>> getSolution() {
        Map<Long, Map<Long, Double>> numSolution = solver.getInterpretation();
        Map<Car, List<String>> solution = new TreeMap<>();
        for (Long carId : numSolution.keySet()) {
            Map<Long, Double> pointAndMass = numSolution.get(carId);
            List<String> info = getInfo(pointAndMass);
            solution.put(carRepository.findById(carId).get(), info);
        }
        return solution;
    }

    /**
     * set condition for solution finder
     * @param points Point of deliver
     * @param order How much cargo we must deliver
     */
    @Override
    public void setConditions(Point[] points, Double[] order) {
        this.points = points;
        this.order = order;
    }

    private List<String> getInfo(Map<Long, Double> pointAndMass) {
        List<String> info = new ArrayList<>();
        //Exception ???
        for (Long pointId : pointAndMass.keySet()) {
            info.add(pointRepository.findById(pointId).get().getName());
            info.add(pointAndMass.get(pointId).toString());
        }
        // ckolko ato stoit? Dobavit?????
        return info;
    }


}
