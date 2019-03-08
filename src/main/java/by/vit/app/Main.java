package by.vit.app;

import by.vit.dao.*;
import by.vit.dao.impl.*;
import by.vit.model.Car;
import by.vit.model.CarModel;
import by.vit.model.Point;
import by.vit.model.Road;


public class Main {
    private static final String[] towns = {"Grodno", "Lida", "Volkovysk"};
    private static final PointDAO pointDAO = PointDAOImpl.getInstance();
    private static final RoadDAO roadDAO = RoadDAOImpl.getInstance();
    private static final CarModelDAO carModelDAO = CarModelDAOImpl.getInstance();
    private static final CarDAO carDAO = CarDAOImpl.getInstance();
    private static final UserDAO userDAO = UserDAOImpl.getInstance();
    private static final TransporterDAO transporterDAO = TransporterDAOImpl.getInstance();

    public static void main(String[] args) {
//        for (int i = 0; i < towns.length; i++) {
//            Point temp = new Point(towns[i]);
//            Long l = new Long(i);
//            temp.setId(l);
//            pointDAO.save(temp);
//        }
        Point point = pointDAO.getPointByName("Lida");
        Point point1 = pointDAO.getPointByName("Grodno");
        //roadDAO.save(new Road(point, point1, 115D));
        carModelDAO.save(new CarModel("Renault Master L3H2",2.5D,13D));
        CarModel carModel = carModelDAO.getOne(1L);

        carDAO.save(new Car(carModel,));
    }
}
