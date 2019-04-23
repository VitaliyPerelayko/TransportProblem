package by.vit.service.impl;

import by.vit.config.SolverConfiguration;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BestWayFinderImpl implements BestWayFinder {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SolverConfiguration.class);
    private Point[] points;
    private Double[] order;
    private RoadRepository roadRepository;
    private CarRepository carRepository;
    private PointRepository pointRepository;
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

    @Override
    public Map<Long, Map<Long, Double>> getSolution() {
        return solver.getInterpretation();
    }

    @Override
    public void setConditions(Point[] points, Double[] order) {
        this.points = points;
        this.order = order;
    }


}
