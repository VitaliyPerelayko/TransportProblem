package by.vit.app;

import by.vit.model.*;
import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.impl.CLPSolverImpl;
import by.vit.service.transportproblemsolve.impl.YanQiDistanceMatrixFinderImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SolverExample {
    public static void main(String[] args) {
        Road[] roads = {
                getRoad(getRoadId(1l, 2l), 4D),
                getRoad(getRoadId(1l, 5l), 1D),
                getRoad(getRoadId(2l, 3l), 2D),
                getRoad(getRoadId(2l, 5l), 2D),
                getRoad(getRoadId(2l, 6l), 1D),
                getRoad(getRoadId(3l, 6l), 2D),
                getRoad(getRoadId(3l, 4l), 2D),
                getRoad(getRoadId(4l, 7l), 3D)
        };

        Point[] points = {getPoint(2L),
                getPoint(1L),
                getPoint(4L),
                getPoint(7L),
        };

        CarModel[] carModels = {
                getCarModel(1L,"Renault",50D,150D),
                getCarModel(2L,"Renault",20D,150D),
                getCarModel(3L,"Renault",30D,150D)
        };

        List<Car> cars = Arrays.asList(
                getCar(1L,carModels[0],1.9),
                getCar(2L,carModels[1],1.3),
                getCar(3L,carModels[2],1.5)
        );

        Double[] order = {20D,25D,15D};

        YanQiDistanceMatrixFinderImpl distanceMatrix = new YanQiDistanceMatrixFinderImpl(localizedMessageSource, roads,points);

        Double [][] distance = distanceMatrix.getDistanceMatrix();

        for (int i = 0; i<distance.length;i++){
            for (int j = 0; j<distance.length;j++) {
                System.out.print(distance[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("=====================================================");

        ConditionTP conditionTP = new ConditionTP(distanceMatrix,cars,order);

        BigDecimal[][] costMatrix = conditionTP.getCostMatrix();

        for (int i = 0; i<costMatrix.length;i++){
            for (int j = 0; j<costMatrix.length+1;j++) {
                System.out.print(costMatrix[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("=====================================================");


        Double[] restrictionOfCars = conditionTP.getRestrictionOfCars();

        for (Double d:restrictionOfCars){
            System.out.print(d+"  ");
        }

        System.out.println("\n=====================================================");

        Double[] restrictionOfOrders = conditionTP.getRestrictionOfOrder();

        for (Double d:restrictionOfOrders){
            System.out.print(d+"  ");
        }

        CLPSolverImpl solver = new CLPSolverImpl(conditionTP);

        Double[][] solveMatrix = solver.getMatrixOfSolve();

        for (int i = 0; i<solveMatrix.length;i++){
            for (int j = 0; j<solveMatrix.length+1;j++) {
                System.out.print(solveMatrix[i][j]+" ");
            }
            System.out.println();
        }

        Map<Long,List<String>> interpretation = solver.getInterpretation();

        System.out.println(interpretation);


    }

    private static RoadId getRoadId(Long p1, Long p2) {
        return new RoadId(p1, p2);
    }

    private static Road getRoad(RoadId roadId, Double distance) {
        Road road = new Road();
        road.setId(roadId);
        road.setDistance(distance);
        return road;
    }

    private static Point getPoint(Long id){
        Point point = new Point();
        point.setId(id);
        return point;
    }

    private static CarModel getCarModel(Long id, String name, Double tonnage, Double space){
        CarModel carModel = new CarModel(name, tonnage, space);
        carModel.setId(id);
        return carModel;
    }

    private static Car getCar(Long id, CarModel carModel, Double cost){
        Car car = new Car();
        car.setId(id);
        car.setCarModel(carModel);
        car.setCost(cost);
        return car;
    }

}
