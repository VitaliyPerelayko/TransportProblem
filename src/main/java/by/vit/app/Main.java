package by.vit.app;

import by.vit.config.DatabaseConfiguration;
import by.vit.model.*;
import by.vit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

/**
 * Main class for tasting database module
 */
@Component
public class Main {
    private static final String[] towns = {"Grodno", "Lida", "Volkovysk"};
    private PointRepository pointRepository;
    private RoadRepository roadRepository;
    private CarModelRepository carModelRepository;
    private CarRepository carRepository;
    private UserRepository userRepository;
    private TransporterRepository transporterRepository;
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotatedClassApplicationContext = new AnnotationConfigApplicationContext(DatabaseConfiguration.class);
        Main main = annotatedClassApplicationContext.getBean("main", Main.class);

        //Create add read  objects
        for (int i = 0; i < towns.length; i++) {
            Point temp = new Point(towns[i]);
            main.getPointRepository().save(temp);
        }
        Point point = main.getPointRepository().findByName("Lida");
        Point point1 = main.getPointRepository().findByName("Grodno");

        saveRoad(point, point1, 115D, main);

        main.getRoleRepository().save(new Role("transporter"));
        Role role = main.getRoleRepository().getOne(1L);

        saveTransporter("Jack", "Sparrow", "103", "bla-bla@yahoo.com", role, "1234567", main);
        Transporter transporter = main.getTransporterRepository().getOne(1L);

        saveUser("Vadim", "Vadim", "+375 29", "@gmail.com", role, main);
        Optional<User> userTemp = main.getUserRepository().findById(2L);
        User user = userTemp.orElseThrow(RuntimeException::new);

        main.getCarModelRepository().save(new CarModel("Renault Master L3H2", 2.5D, 13D));
        CarModel carModel = main.getCarModelRepository().getOne(1L);

        saveCar(carModel, point, transporter, 1.75D, main);
        Car car = main.getCarRepository().getOne(1L);
        System.out.println(main.getCarRepository().findCarsByTonnageAfter(2D));

        //Update objects
        user.setName("Stalin");

        main.getUserRepository().save(user);
        main.getUserRepository().findById(2L).ifPresent(System.out::println);

        //Delete objects
        main.getUserRepository().delete(user);
        // and so one

    }

    private static void saveRoad(Point point1, Point point2, Double distance, Main main) {
        Road road = new Road();
        road.setPoint1(point1);
        road.setPoint2(point2);
        road.setDistance(distance);
        main.getRoadRepository().save(road);
    }

    private static void saveUser(String name, String surname, String phone, String eMail, Role role, Main main) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.seteMail(eMail);
        user.setRoles(Collections.singleton(role));
        main.getUserRepository().save(user);
    }

    private static void saveCar(CarModel carModel, Point point, Transporter transporter, Double cost, Main main) {
        Car car = new Car();
        car.setCarModel(carModel);
        car.setPoint(point);
        car.setTransporter(transporter);
        car.setCost(cost);
        main.getCarRepository().save(car);
    }

    private static void saveTransporter(
            String name, String surname, String phone, String eMail, Role role, String license, Main main) {
        Transporter transporter = new Transporter();
        transporter.setName(name);
        transporter.setSurname(surname);
        transporter.setPhone(phone);
        transporter.seteMail(eMail);
        transporter.setRoles(Collections.singleton(role));
        transporter.setLicense(license);
        main.getTransporterRepository().save(transporter);
    }

    public PointRepository getPointRepository() {
        return pointRepository;
    }

    @Autowired
    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public RoadRepository getRoadRepository() {
        return roadRepository;
    }

    @Autowired
    public void setRoadRepository(RoadRepository roadRepository) {
        this.roadRepository = roadRepository;
    }

    public CarModelRepository getCarModelRepository() {
        return carModelRepository;
    }

    @Autowired
    public void setCarModelRepository(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public CarRepository getCarRepository() {
        return carRepository;
    }

    @Autowired
    public void setCarRepository(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TransporterRepository getTransporterRepository() {
        return transporterRepository;
    }

    @Autowired
    public void setTransporterRepository(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}

