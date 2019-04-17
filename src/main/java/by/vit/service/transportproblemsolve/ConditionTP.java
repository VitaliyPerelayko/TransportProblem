package by.vit.service.transportproblemsolve;

import by.vit.model.*;

import java.util.List;

public class ConditionTP {

    private Double[] restrictionOfCars;
    private Double[] restrictionOfOrder;
    private Double[][] costMatrix;
    private boolean flagOfPseudoPoint;
    private int lengthOfListCars;
    private int lengthOfListPoints;
    private Double difference;
    private Long[] carsId;
    private Long[] pointsId;

    //big cost for unreachable road
    private static final Double UNREACHABLE = 999999D;

    public ConditionTP(DistanceMatrixFinder distanceMatrixFinder, List<Car> cars, Double[] order){
        this.lengthOfListCars = cars.size();
        this.lengthOfListPoints = distanceMatrixFinder.getDistanceMatrix().length-1;
        decideAboutFlag(cars,order);
        this.costMatrix = getCostMatrix(distanceMatrixFinder.getDistanceMatrix(),getCarCost(cars));
        this.restrictionOfCars = getRestrictionOfCars(cars);
        this.restrictionOfOrder = getRestrictionOfOrder(order,flagOfPseudoPoint);
        carsId = new Long[cars.size()];
        for (int i = 0; i < carsId.length; i++){
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

    public Double[][] getCostMatrix() {
        return costMatrix;
    }

    public Long[] getCarsId() {
        return carsId;
    }

    public Long[] getPointsId() {
        return pointsId;
    }

    private void decideAboutFlag(List<Car> cars, Double[] order){
        Double carsTonnage = new Double(0);
        for (Car c:cars){
            carsTonnage += c.getCarModel().getTonnage();
        }
        Double sumOfOrder = new Double(0);
        for (Double d:order){
            sumOfOrder += d;
        }

        if (carsTonnage>sumOfOrder){
            this.flagOfPseudoPoint = true;
            this.difference = carsTonnage-sumOfOrder;
        }else {
            this.flagOfPseudoPoint = false;
            this.difference = 0D;
        }
    }

    private Double[][] getCostMatrix(Double[][] distanceMatrix, Double[] carCost) {
        int n = (lengthOfListPoints-1)*lengthOfListCars;

        Double [][] costMatrix1 = null;

        if (flagOfPseudoPoint) {
            costMatrix1 = new Double[lengthOfListPoints*lengthOfListCars]
                    [lengthOfListPoints*lengthOfListCars+1];
            for (int a = 0; a<lengthOfListCars; a++){
                for (int i = 0; i<lengthOfListPoints; i++){
                    int foo = i+a*lengthOfListPoints;
                    for (int j = 0; j < n;j++){
                        if ((j-a)%lengthOfListCars==0){
                            int p=Math.abs(j-a)/lengthOfListCars;
                            costMatrix1[foo][j] = distanceMatrix[i+1][2+p]*carCost[a];
                        } else{
                            costMatrix1[foo][j] = UNREACHABLE;
                        }
                    }
                    for (int j = n,p = 0; j < lengthOfListCars*lengthOfListPoints;j++,p++){
                        costMatrix1[foo][j] = distanceMatrix[i+1][2+p]*carCost[a];
                    }
                    costMatrix1[foo][lengthOfListCars*lengthOfListPoints] = 0D;
                }
            }
        }else {
            costMatrix = new Double[lengthOfListPoints*lengthOfListCars]
                    [lengthOfListPoints*lengthOfListCars];
        }



        return costMatrix1;
    }

    private Double[] getCarCost(List<Car> cars) {
        int n = cars.size();
        Double[] carCost = new Double[n];
        for (int i = 0; i < n; i++) {
            carCost[i] = cars.get(i).getCost();
        }
        return carCost;
    }

    private Double[] getRestrictionOfOrder(Double[] order,boolean flagOfPseudoPoint) {
        int n = lengthOfListPoints-1;
        Double[] restrictionOfOrder = null;
        Double[] carsTonnage = new Double[lengthOfListCars];
        for (int i = 0; i < lengthOfListCars; i++){
            carsTonnage[i] = restrictionOfCars[i*lengthOfListPoints];
        }
        if (flagOfPseudoPoint) {
            restrictionOfOrder = new Double[lengthOfListPoints*lengthOfListCars+1];
            for(int i = 0; i < n; i++){
                for (int j = 0; j < lengthOfListCars; j++) {
                    restrictionOfOrder[j+i*lengthOfListCars] = carsTonnage[j];
                }
            }
            for (int i = n,j=0; j < order.length;j++){
                restrictionOfOrder[j+i*lengthOfListCars] = order[j];
            }
            restrictionOfOrder[restrictionOfOrder.length-1] = difference;
        }else {
            restrictionOfOrder = new Double[lengthOfListPoints*lengthOfListCars];
        }
        return restrictionOfOrder;
    }

    private Double[] getRestrictionOfCars(List<Car> cars) {
        Double[] carRestriction = new Double[lengthOfListPoints*lengthOfListCars];
        for (int i = 0; i < lengthOfListCars; i++) {
            Double temp = cars.get(i).getCarModel().getTonnage();
            for (int j = 0; j < lengthOfListPoints; j++) {
                carRestriction[j+i*lengthOfListPoints] = temp;
            }
        }
        return carRestriction;
    }
}
