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


    private List<Long> carsId;

    //big cost for unreachable road
    private static final Double UNREACHABLE = 999999D;

    public ConditionTP(YanQiDistanceMatrixImpl distanceMatrix, List<Car> cars, boolean flag, Double[] ordrer){
        this.lengthOfListCars = cars.size();
        this.lengthOfListPoints = distanceMatrix.getDistanceMatrix().length-1;
        decideAboutFlag(cars,ordrer);
        this.costMatrix = getCostMatrix(distanceMatrix.getDistanceMatrix(),getCarCost(cars));
        this.restrictionOfCars = getRestrictionOfCars(cars);
        this.restrictionOfOrder = getRestrictionOfOrder(ordrer,flag);
        //////////cars
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

    public List<Long> getCarsId() {
        return carsId;
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


    public static void main(String[] args) {
//        Road[] roads = {
//                getRoad(getRoadId(1l, 2l), 4D),
//                getRoad(getRoadId(1l, 5l), 1D),
//                getRoad(getRoadId(2l, 3l), 2D),
//                getRoad(getRoadId(2l, 5l), 2D),
//                getRoad(getRoadId(2l, 6l), 1D),
//                getRoad(getRoadId(3l, 6l), 2D),
//                getRoad(getRoadId(3l, 4l), 2D),
//                getRoad(getRoadId(4l, 7l), 3D)
//        };
//
//        Point[] points = {getPoint(2L),
//                getPoint(1L),
//                getPoint(4L),
//                getPoint(7L),
//        };
//
//        CarModel[] carModels = {
//                getCarModel(1L,"Renault",50D,150D),
//                getCarModel(2L,"Renault",20D,150D),
//                getCarModel(3L,"Renault",30D,150D)
//        };
//
//        List<Car> cars = Arrays.asList(
//                getCar(1L,carModels[0],1.9),
//                getCar(2L,carModels[1],1.3),
//                getCar(3L,carModels[2],1.5)
//         );
//
//        Double[] order = {20D,25D,15D};
//
//        YanQiDistanceMatrixImpl distanceMatrix = new YanQiDistanceMatrixImpl(roads,points);
//
//        Double [][] distance = distanceMatrix.getDistanceMatrix();
//
//        for (int i = 0; i<distance.length;i++){
//            for (int j = 0; j<distance.length;j++) {
//                System.out.print(distance[i][j]+" ");
//            }
//            System.out.println();
//        }
//
//        System.out.println("=====================================================");
//
//        ConditionTP conditionTP = new ConditionTP(distanceMatrix,cars,true,order);
//
//        Double[][] costMatrix = conditionTP.getCostMatrix();
//
//        for (int i = 0; i<costMatrix.length;i++){
//            for (int j = 0; j<costMatrix.length+1;j++) {
//                System.out.print(costMatrix[i][j]+" ");
//            }
//            System.out.println();
//        }
//
//        System.out.println("=====================================================");
//
//
//        Double[] restrictionOfCars = conditionTP.getRestrictionOfCars();
//
//        for (Double d:restrictionOfCars){
//            System.out.print(d+"  ");
//        }
//
//        System.out.println("\n=====================================================");
//
//        Double[] restrictionOfOrders = conditionTP.getRestrictionOfOrder();
//
//        for (Double d:restrictionOfOrders){
//            System.out.print(d+"  ");
//        }



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
