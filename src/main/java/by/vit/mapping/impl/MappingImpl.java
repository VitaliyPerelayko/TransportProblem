package by.vit.mapping.impl;

import by.vit.dto.CarModelDTO;
import by.vit.dto.PointDTO;
import by.vit.dto.RoleDTO;
import by.vit.dto.request.CarRequestDTO;
import by.vit.dto.request.RoadRequestDTO;
import by.vit.dto.request.TaskDTO;
import by.vit.dto.request.UserRequestDTO;
import by.vit.dto.response.*;
import by.vit.mapping.Mapping;
import by.vit.model.*;
import by.vit.model.solution.Solution;
import by.vit.model.solution.SolutionCar;
import by.vit.service.CarModelService;
import by.vit.service.PointService;
import by.vit.service.RoleService;
import by.vit.service.UserService;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom mapping service
 */
@Service
public class MappingImpl implements Mapping {
    private final Mapper mapper;
    private final RoleService roleService;
    private final PointService pointService;
    private final CarModelService carModelService;
    private final UserService userService;



    public MappingImpl(Mapper mapper, RoleService userService, PointService pointService,
                       CarModelService carModelService, UserService userService1) {
        this.mapper = mapper;
        this.roleService = userService;
        this.pointService = pointService;
        this.carModelService = carModelService;
        this.userService = userService1;
    }

    /**
     * maps the taskDTO to the array of points
     *
     * @param taskDTO it's list of points and list of orders (mass of cargo, that we need to deliver to the point)
     * @return Point[], first point is point from which deliver will start. The others is delivery points.
     */
    @Override
    public Point[] mapTaskToPoint(TaskDTO taskDTO) {
        final Point[] points = new Point[taskDTO.getPointName().size()];
        return taskDTO.getPointName().stream().map(
                pointService::findByName).collect(Collectors.toList()).toArray(points);
    }

    /**
     * Maps two number to RoadId
     *
     * @param id1 pointId
     * @param id2 pointId
     * @return RoadId
     */
    @Override
    public RoadId mapToRoadId(Long id1, Long id2) {
        return new RoadId(id1, id2);
    }

    /**
     * map RoadRequestDTO to Road
     *
     * @param roadRequestDTO RoadRequestDTO
     * @return Road
     */
    @Override
    public Road mapRoadRequestDTOToRoad(RoadRequestDTO roadRequestDTO){
        final Road road = new Road();
        road.setDistance(roadRequestDTO.getDistance());
        road.setId(mapToRoadId(roadRequestDTO.getPoint1id(),roadRequestDTO.getPoint2id()));
        return road;
    }

    /**
     * map Road to RoadResponseDTO
     *
     * @param road road
     * @return RoadResponseDTO
     */
    @Override
    public RoadResponseDTO mapRoadToRoadResponseDTO(Road road){
        final RoadResponseDTO roadResponseDTO = new RoadResponseDTO();
        roadResponseDTO.setPoint1(mapper.map(road.getPoint1(), PointDTO.class));
        roadResponseDTO.setPoint2(mapper.map(road.getPoint2(), PointDTO.class));
        roadResponseDTO.setDistance(road.getDistance());
        return roadResponseDTO;
    }

    /**
     * map CarRequestDTO to Car
     *
     * @param carRequestDTO carRequestDTO
     * @return Car
     */
    @Override
    public Car mapCarRequestDTOToCar(CarRequestDTO carRequestDTO){
        final Car car = new Car();
        if (carRequestDTO.getId()!=null) {
            car.setId(carRequestDTO.getId());
        }
        car.setCarModel(carModelService.findById(carRequestDTO.getCarModelId()));
        car.setPoint(pointService.findById(carRequestDTO.getPointId()));
        car.setTransporter(userService.findById(carRequestDTO.getTransporterId()));
        car.setCost(carRequestDTO.getCost());
        return car;
    }

    /**
     * map Car to CarResponseDTO
     *
     * @param car car
     * @return CarResponseDTO
     */
    @Override
    public CarResponseDTO mapCarToCarResponseDTO(Car car){
        final CarResponseDTO carResponseDTO = new CarResponseDTO();
        carResponseDTO.setId(car.getId());
        carResponseDTO.setCarModel(mapper.map(car.getCarModel(), CarModelDTO.class));
        carResponseDTO.setCost(car.getCost());
        carResponseDTO.setPoint(mapper.map(car.getPoint(),PointDTO.class));
        carResponseDTO.setTransporter(mapper.map(car.getTransporter(),ShortUserResponseDTO.class));
        return carResponseDTO;
    }

    /**
     * maps the Solution to the SolutionDTO
     *
     * @param solution solution
     * @return SolutionResponseDTO
     */
    @Override
    public SolutionResponseDTO mapSolutionToSolutionResponseDTO(Solution solution) {
        final SolutionResponseDTO solutionResponseDTO = new SolutionResponseDTO();

        solutionResponseDTO.setId(solution.getId());

        solutionResponseDTO.setDateTime(solution.getDateTime().toString());
        solutionResponseDTO.setSupplier(mapper.map(solution.getSupplier(),ShortUserResponseDTO.class));
        final Set<SolutionCar> solutionCarSet = solution.getCarSet();
        solutionResponseDTO.setCars(solutionCarSet.stream().map(solutionCar ->
                mapCarToCarResponseDTO(solutionCar.getCar())).collect(Collectors.toList()));
        solutionResponseDTO.setRoutes(solutionCarSet.stream().map((solutionCar) ->
                solutionCar.getRoutes().stream().map((route) ->
                        mapper.map(route, RouteResponseDTO.class)).collect(Collectors.toList()))
                .collect(Collectors.toList()));
        return solutionResponseDTO;
    }

    /**
     * maps the UserRequestDTO to the User
     *
     * @param userRequestDTO userRequestDTO
     * @return User
     */
    @Override
    public User mapUserRequestDTOTOUser(UserRequestDTO userRequestDTO) {
        final User user = mapper.map(userRequestDTO, User.class);
        user.setRoles(mapRolesNameToRoles(userRequestDTO.getRolesName()));
        return user;
    }

    /**
     * maps the User to the UserResponseDTO
     *
     * @param user user
     * @return UserResponseDTO
     */
    @Override
    public UserResponseDTO mapUserToUserResponseDTO(User user) {
        final UserResponseDTO userResponseDTO = mapper.map(user, UserResponseDTO.class);
        userResponseDTO.setRoles(mapRoleToRoleDTO(user.getRoles()));
        return userResponseDTO;
    }

    private Set<RoleDTO> mapRoleToRoleDTO(Set<Role> roles){
        return roles.stream().map((role) ->
                mapper.map(role, RoleDTO.class)).collect(Collectors.toSet());
    }

    private Set<Role> mapRolesNameToRoles(Set<String> roles){
        return roles.stream()
                .map(roleService::findByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
