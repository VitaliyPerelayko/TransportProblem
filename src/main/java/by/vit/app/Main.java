package by.vit.app;

import by.vit.dao.*;
import by.vit.dao.impl.*;
import by.vit.model.*;


public class Main {
    private static final String[] towns = {"Grodno", "Lida", "Volkovysk"};
    private static final PointDAO pointDAO = PointDAOImpl.getInstance();
    private static final RoadDAO roadDAO = RoadDAOImpl.getInstance();
    private static final CarModelDAO carModelDAO = CarModelDAOImpl.getInstance();
    private static final CarDAO carDAO = CarDAOImpl.getInstance();
    private static final UserDAO userDAO = UserDAOImpl.getInstance();
    private static final TransporterDAO transporterDAO = TransporterDAOImpl.getInstance();
    private static final RoleDAO roleDAO = RoleDAOImpl.getInstance();

    public static void main(String[] args) {
//        for (int i = 0; i < towns.length; i++) {
//            Point temp = new Point(towns[i]);
//            Long l = new Long(i);
//            temp.setId(l);
//            pointDAO.save(temp);
//        }
        Point point = pointDAO.getPointByName("Lida");
        Point point1 = pointDAO.getPointByName("Grodno");

//        saveRoad(point, point1, 115D);

//        roleDAO.save(new Role("transporter"));
        Role role = roleDAO.getOne(2L);

//        saveUser("Vadim","Vadim","+375 29","@gmail.com",role);
        User user = userDAO.getOne(1L);

//        transporterDAO.save(new Transporter(user,"1234567890PP"));
        Transporter transporter = transporterDAO.getOne(2L);

//        carModelDAO.save(new CarModel("Renault Master L3H2",2.5D,13D));
        CarModel carModel = carModelDAO.getOne(1L);

        saveCar(carModel,point,transporter,1.75D);
    }

    public static void saveRoad(Point point1,Point point2,Double distance){
        Road road = new Road();
        road.setPoint1(point1);
        road.setPoint2(point2);
        road.setDistance(distance);
        roadDAO.save(road);
    }
    public static void saveUser(String name, String surname, String phone, String eMail, Role role){
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.seteMail(eMail);
        user.setRole(role);
        userDAO.save(user);
    }

    public static void saveCar(CarModel carModel, Point point, Transporter transporter, Double cost){
        Car car = new Car();
        car.setCarModel(carModel);
        car.setPoint(point);
        car.setTransporter(transporter);
        car.setCost(cost);
        carDAO.save(car);
    }
}

