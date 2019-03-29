package by.vit.app;

import by.vit.config.AppConfiguration;
import by.vit.repository.*;
import by.vit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Main {
    private static final String[] towns = {"Grodno", "Lida", "Volkovysk"};
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private RoadRepository roadRepository;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransporterRepository transporterRepository;
    @Autowired
    private RoleRepository roleRepository;



    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotatedClassApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Main main = annotatedClassApplicationContext.getBean("main", Main.class);

        //Create add read  objects
        for (int i = 0; i < towns.length; i++) {
            Point temp = new Point(towns[i]);
            main.pointRepository.save(temp);
        }
        Point point = main.pointRepository.findByName("Lida");
        Point point1 = main.pointRepository.findByName("Grodno");

        saveRoad(point, point1, 115D,main);

        main.roleRepository.save(new Role("transporter"));
        Role role = main.roleRepository.getOne(1L);

        saveTransporter("Jack","Sparrow","103","bla-bla@yahoo.com",role,"1234567",main);
        Transporter transporter = main.transporterRepository.getOne(1L);

        saveUser("Vadim","Vadim","+375 29","@gmail.com",role,main);
        Optional<User> userTemp = main.userRepository.findById(2L);
        User user = userTemp.orElseThrow(RuntimeException::new);

        main.carModelRepository.save(new CarModel("Renault Master L3H2",2.5D,13D));
        CarModel carModel = main.carModelRepository.getOne(1L);

        saveCar(carModel,point,transporter,1.75D,main);
        Car car = main.carRepository.getOne(1L);
        System.out.println(main.carRepository.findCarsByTonnageAfter(2D));

        //Update objects
        user.setName("Stalin");
        main.userRepository.save(user);
        main.userRepository.findById(2L).ifPresent(System.out::println);

        //Delete objects
        main.userRepository.delete(user);
        // and so one

    }

    private static void saveRoad(Point point1, Point point2, Double distance, Main main) {
        Road road = new Road();
        road.setPoint1(point1);
        road.setPoint2(point2);
        road.setDistance(distance);
        main.roadRepository.save(road);
    }

    private static void saveUser(String name, String surname, String phone, String eMail, Role role,Main main) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.seteMail(eMail);
        user.setRole(role);
        main.userRepository.save(user);
    }

    private static void saveCar(CarModel carModel, Point point, Transporter transporter, Double cost, Main main) {
        Car car = new Car();
        car.setCarModel(carModel);
        car.setPoint(point);
        car.setTransporter(transporter);
        car.setCost(cost);
        main.carRepository.save(car);
    }

    private static void saveTransporter(
            String name, String surname, String phone, String eMail, Role role, String license, Main main){
        Transporter transporter = new Transporter();
        transporter.setName(name);
        transporter.setSurname(surname);
        transporter.setPhone(phone);
        transporter.seteMail(eMail);
        transporter.setRole(role);
        transporter.setLicense(license);
        main.transporterRepository.save(transporter);
    }
}

