package by.vit.service.transportproblemsolve;

import by.vit.model.Car;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class contains information to solve transport problem.
 */
public class ConditionTP {

    //big cost for unreachable road
    private static final BigDecimal UNREACHABLE = new BigDecimal("999999");
    private Double[] restrictionOfCars;
    private Double[] restrictionOfOrder;
    private BigDecimal[][] costMatrix;
    private boolean flagOfPseudoPoint;
    private int lengthOfListCars;
    private int lengthOfListPoints;
    private Double difference;
    private Long[] carsId;
    private Long[] pointsId;

    public ConditionTP(DistanceMatrixFinder distanceMatrixFinder, List<Car> cars,
                       Double[] order, boolean flagOfPseudoPoint, Double difference) {
        this.flagOfPseudoPoint = flagOfPseudoPoint;
        this.difference = difference;
        this.lengthOfListCars = cars.size();
        this.lengthOfListPoints = distanceMatrixFinder.getDistanceMatrix().length - 1;
        getCostMatrix(distanceMatrixFinder.getDistanceMatrix(), getCarCost(cars));
        this.restrictionOfCars = getRestrictionOfCars(cars);
        getRestrictionOfOrder(order, flagOfPseudoPoint);
        carsId = new Long[cars.size()];
        for (int i = 0; i < carsId.length; i++) {
            carsId[i] = cars.get(i).getId();
        }
        this.pointsId = distanceMatrixFinder.getPointsId();
    }

    public Double[] getRestrictionOfCars() {
        return restrictionOfCars;
    }

    public Double[] getRestrictionOfOrder() {
        return restrictionOfOrder;
    }

    public BigDecimal[][] getCostMatrix() {
        return costMatrix;
    }

    public Long[] getCarsId() {
        return carsId;
    }

    public Long[] getPointsId() {
        return pointsId;
    }

    private void getCostMatrix(Double[][] distanceMatrix, Double[] carCost) {
        int n = (lengthOfListPoints - 1) * lengthOfListCars;

        if (flagOfPseudoPoint) {
            costMatrix = new BigDecimal[lengthOfListPoints * lengthOfListCars]
                    [n + lengthOfListPoints];
            for (int a = 0; a < lengthOfListCars; a++) {
                for (int i = 0; i < lengthOfListPoints; i++) {
                    int foo = i + a * lengthOfListPoints;
                    fillCostMatrix(distanceMatrix, carCost, a, i, n, foo);
                    costMatrix[foo][n + lengthOfListPoints - 1] = BigDecimal.ZERO;
                }
            }
        } else {
            costMatrix = new BigDecimal[lengthOfListPoints * lengthOfListCars]
                    [n + lengthOfListPoints-1];
            for (int a = 0; a < lengthOfListCars; a++) {
                for (int i = 0; i < lengthOfListPoints; i++) {
                    int foo = i + a * lengthOfListPoints;
                    fillCostMatrix(distanceMatrix, carCost, a, i, n, foo);
                }
            }
        }
    }

    private Double[] getCarCost(List<Car> cars) {
        int n = cars.size();
        Double[] carCost = new Double[n];
        for (int i = 0; i < n; i++) {
            carCost[i] = cars.get(i).getCost();
        }
        return carCost;
    }

    private void getRestrictionOfOrder(Double[] order, boolean flagOfPseudoPoint) {
        int n = lengthOfListPoints - 1;
        Double[] carsTonnage = new Double[lengthOfListCars];
        for (int i = 0; i < lengthOfListCars; i++) {
            carsTonnage[i] = restrictionOfCars[i * lengthOfListPoints];
        }
        if (flagOfPseudoPoint) {
            restrictionOfOrder = new Double[n * (lengthOfListCars + 1) + 1];
            fillRestrictionOfOrder(carsTonnage, order, n);
            restrictionOfOrder[restrictionOfOrder.length - 1] = difference;
        } else {
            restrictionOfOrder = new Double[n * (lengthOfListCars + 1)];
            fillRestrictionOfOrder(carsTonnage, order, n);
        }
    }

    private Double[] getRestrictionOfCars(List<Car> cars) {
        Double[] carRestriction = new Double[lengthOfListPoints * lengthOfListCars];
        for (int i = 0; i < lengthOfListCars; i++) {
            Double temp = cars.get(i).getCarModel().getTonnage();
            for (int j = 0; j < lengthOfListPoints; j++) {
                carRestriction[j + i * lengthOfListPoints] = temp;
            }
        }
        return carRestriction;
    }

    private void fillCostMatrix(Double[][] distanceMatrix, Double[] carCost,
                                int a, int i, int n, int foo) {
        for (int j = 0; j < n; j++) {
            if ((j - a) % lengthOfListCars == 0) {
                int p = Math.abs(j - a) / lengthOfListCars;
                BigDecimal cost = new BigDecimal(distanceMatrix[i + 1][2 + p].toString())
                        .multiply(new BigDecimal(carCost[a].toString()));
                costMatrix[foo][j] = cost;
            } else {
                costMatrix[foo][j] = UNREACHABLE;
            }
        }
        for (int j = n, p = 0; j < n + lengthOfListPoints - 1; j++, p++) {
            BigDecimal cost = new BigDecimal(distanceMatrix[i + 1][2 + p].toString())
                    .multiply(new BigDecimal(carCost[a].toString()));
            costMatrix[foo][j] = cost;
        }
    }

    private void fillRestrictionOfOrder(Double[] carsTonnage, Double[] order, int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < lengthOfListCars; j++) {
                restrictionOfOrder[j + i * lengthOfListCars] = carsTonnage[j];
            }
        }
        for (int i = n, j = 0; j < order.length; j++) {
            restrictionOfOrder[j + i * lengthOfListCars] = order[j];
        }
    }
}
