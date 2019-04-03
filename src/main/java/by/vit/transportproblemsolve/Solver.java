package by.vit.transportproblemsolve;


import by.vit.model.*;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.util.Arrays;
import java.util.List;

public class Solver {
    static {
        System.load("C:\\or-tools\\lib\\jniortools.dll");
        //System.load("E:\\Work\\vitaluga\\PROGRAMMING\\HalpfulAPP\\or-tools_VisualStudio2017-64bit_v7.0.6546\\lib\\protobuf.jar");
        //System.loadLibrary("jniortools");
    }


    private Double[][] matrixOfSolve;

    public Solver(ConditionTP conditionTP){
        solveTP(conditionTP);
    }

    private void solveTP(ConditionTP conditionTP){
        Double[][] costMatrix = conditionTP.getCostMatrix();
        Double[] restrictionOfCar = conditionTP.getRestrictionOfCars();
        Double[] restrictionOfOrder = conditionTP.getRestrictionOfOrder();
        int rowsLength = costMatrix[0].length;
        int linesLength = costMatrix.length;


        MPSolver solver = createSolver();

        //Create variable x[i][j]
        MPVariable[][] variables = new MPVariable[linesLength][rowsLength];
        // Create the objective function
        MPObjective objective = solver.objective();
        objective.setMinimization();
        // Create restriction for cars
        MPConstraint[] constraintsCar = new MPConstraint[restrictionOfCar.length];
        // Create restriction for order
        MPConstraint[] constraintsOrder = new MPConstraint[restrictionOfOrder.length];
        for (int i = 0; i<restrictionOfOrder.length; i++){
            Double order = restrictionOfOrder[i];
            constraintsCar[i] = solver.makeConstraint(order,order);
        }

        for (int i = 0; i < linesLength; i++){
            Double bound = restrictionOfCar[i];
            for (int j = 0; j < rowsLength; j++){
                //variable
                variables[i][j] = solver.makeIntVar(0d,bound,"x"+i+j);
                //coefficient
                objective.setCoefficient(variables[i][j],costMatrix[i][j]);
                //restriction
                constraintsCar[i] = solver.makeConstraint(bound,bound);
                constraintsCar[i].setCoefficient(variables[i][j],1);
                constraintsOrder[j].setCoefficient(variables[i][j],1);
            }
        }

        solver.solve();
        for (int i = 0; i < linesLength; i++){
            for (int j = 0; j < rowsLength; j++){
                matrixOfSolve[i][j] = variables[i][j].solutionValue();
            }
        }
    }

    private static MPSolver createSolver () {
        return new MPSolver("Solver",
                MPSolver.OptimizationProblemType.valueOf("GLOP_LINEAR_PROGRAMMING"));
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

        DistanceMatrix distanceMatrix = new DistanceMatrix(roads,points);

        Double [][] distance = distanceMatrix.getDistanseMatrix();

        for (int i = 0; i<distance.length;i++){
            for (int j = 0; j<distance.length;j++) {
                System.out.print(distance[i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("=====================================================");

        ConditionTP conditionTP = new ConditionTP(distanceMatrix,cars,true,order);

        Double[][] costMatrix = conditionTP.getCostMatrix();

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

        Solver solver = new Solver(conditionTP);

        Double[][] solveMatrix = solver.matrixOfSolve;

        for (int i = 0; i<solveMatrix.length;i++){
            for (int j = 0; j<solveMatrix.length+1;j++) {
                System.out.print(solveMatrix[i][j]+" ");
            }
            System.out.println();
        }


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
