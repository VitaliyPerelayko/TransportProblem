package by.vit.dto.response;

import java.util.List;

public class SolutionResponseDTO {

    private Long id;

    private String dateTime;

    private UserResponseDTO supplier;

    private List<CarResponseDTO> cars;

    /**
     * Each list<Long> it's routes of car from field carsId
     * <p>
     * Example:
     * carsId = {car1,car5,car7}
     * routesId = {{route1, route2} of car1,
     * {route6} of car5,
     * {route5, route3, route4} of car7
     * }
     */
    private List<List<RouteResponseDTO>> routes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public UserResponseDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(UserResponseDTO supplierId) {
        this.supplier = supplierId;
    }

    public List<CarResponseDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarResponseDTO> cars) {
        this.cars = cars;
    }

    public List<List<RouteResponseDTO>> getRoutes() {
        return routes;
    }

    public void setRoutes(List<List<RouteResponseDTO>> routes) {
        this.routes = routes;
    }
}
