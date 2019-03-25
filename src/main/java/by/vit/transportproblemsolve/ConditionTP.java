package by.vit.transportproblemsolve;

import by.vit.model.Car;

import java.util.List;
import java.util.Map;

public class ConditionTP {

    private DistanceMatrix distanceMatrix;

    private List<Car> cars;

    private Double[][] getCaostMatrix (Double[][] distanceMatrix, Double[] carCost){
        return null;
    }

    private Double[] getCarCost(List<Car> cars){
        int n = cars.size();
        Double[] carCost = new Double[n];
        for (int i=0;i<n;i++){
            carCost[i] = cars.get(i).getCost();
        }
        return carCost;
    }

    private Double[] getRestrictionOfCars(List<Car> cars){
        int n = cars.size();
        Double[] carRestriction = new Double[n];
        for (int i=0;i<n;i++){
            carRestriction[i] = cars.get(i).getCarModel().getTonage();
        }
        return carRestriction;
    }

//    private Double[] getRestrictionOfOrder(Map<Long,Double> orderByPointId) {
//        return null;
//    }

    public static void main(String[] args) {

    }

}
