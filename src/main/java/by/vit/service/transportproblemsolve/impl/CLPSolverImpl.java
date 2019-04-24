package by.vit.service.transportproblemsolve.impl;


import by.vit.model.*;
import by.vit.service.transportproblemsolve.ConditionTP;
import by.vit.service.transportproblemsolve.SolverOfTP;
import com.quantego.clp.*;

import java.math.BigDecimal;
import java.util.*;


public class CLPSolverImpl implements SolverOfTP {

    private Double[][] matrixOfSolve;
    private Map<Long,List<String>> interpretation;

    public CLPSolverImpl(ConditionTP conditionTP){
        solveTP(conditionTP);
        getInterpretation(conditionTP);
    }

    public Double[][] getMatrixOfSolve() {
        return matrixOfSolve;
    }

    public Map<Long,List<String>> getInterpretation() {
        return interpretation;
    }



    private void getInterpretation(ConditionTP conditionTP){
        Long[] carsId = conditionTP.getCarsId();
        Long[] pointsId = conditionTP.getPointsId();
        BigDecimal[][] costMatrix =  conditionTP.getCostMatrix();

        int n = pointsId.length+1;
        int s = carsId.length*pointsId.length;
        Double zero = 0D;

        interpretation = new TreeMap<>();

        for (int a = 0; a<carsId.length; a++){
            for (int i = a*n; i < (a+1)*n; i++){
                for (int j = 0; j < n-1; j++){
                    Double tonne = matrixOfSolve[i][j+s];
                    BigDecimal cost = null;
                    if (costMatrix[i][j+s].equals(BigDecimal.ZERO)){
                        cost = costMatrix[i][j+s];
                    }else{
                        cost = costMatrix[a*n][j*carsId.length+a];
                    }

                    if (!tonne.equals(zero)){
                        if (interpretation.get((long)a)==null){
                            interpretation.put((long) a,new ArrayList<>());
                        }
                        List<String> infoForCar = interpretation.get((long)a);
                        infoForCar.add(pointsId[j].toString());
                        infoForCar.add(tonne.toString());
                        infoForCar.add(cost.multiply(BigDecimal.valueOf(tonne)).toString());
                    }
                }
            }
        }

    }



    private void solveTP(ConditionTP conditionTP){
        BigDecimal[][] costMatrix = conditionTP.getCostMatrix();
        Double[] restrictionOfCar = conditionTP.getRestrictionOfCars();
        Double[] restrictionOfOrder = conditionTP.getRestrictionOfOrder();
        int rowsLength = costMatrix[0].length;
        int linesLength = costMatrix.length;


        CLP solver = new CLP();

        //Create variable x[i][j]
        CLPVariable[][] variables = new CLPVariable[linesLength][rowsLength];
        // Create restriction for cars
        CLPExpression[] constraintsCar = new CLPExpression[restrictionOfCar.length];
        // Create restriction for order
        CLPExpression[] constraintsOrder = new CLPExpression[restrictionOfOrder.length];

        for (int i = 0; i < linesLength; i++){
            Double bound = restrictionOfCar[i];
            constraintsCar[i] = solver.createExpression();
            for (int j = 0; j < rowsLength; j++){
                if (constraintsOrder[j]==null){
                    constraintsOrder[j] = solver.createExpression();
                }
                //variable
                variables[i][j] = solver.addVariable().bounds(0D,bound);
                //coefficient
                solver.setObjectiveCoefficient(variables[i][j],costMatrix[i][j].doubleValue());
                //restriction
                constraintsCar[i].add(variables[i][j],1);
                constraintsOrder[j].add(variables[i][j],1);
            }
        }
        for (int i = 0; i<restrictionOfCar.length; i++){
            Double tonnage = restrictionOfCar[i];
            constraintsCar[i].eq(tonnage);
        }

        for (int i = 0; i<restrictionOfOrder.length; i++){
            Double order = restrictionOfOrder[i];
            constraintsOrder[i].eq(order);
        }

        solver.minimize();
        matrixOfSolve = new Double[linesLength][rowsLength];
        for (int i = 0; i < linesLength; i++){
            for (int j = 0; j < rowsLength; j++){
                matrixOfSolve[i][j] = variables[i][j].getSolution();
            }
        }
    }

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

        YanQiDistanceMatrixFinderImpl distanceMatrix = new YanQiDistanceMatrixFinderImpl(roads,points);

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

        Double[][] solveMatrix = solver.matrixOfSolve;

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
