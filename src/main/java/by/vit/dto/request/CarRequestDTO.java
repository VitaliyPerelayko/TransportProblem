package by.vit.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * DTO for request entity Car
 */
public class CarRequestDTO {

    private Long id;
    @NotNull(message = "car.carModelName.notNull")
    @NotNull(message = "car.carModelName.notNull")
    @NotEmpty(message = "car.carModelName.size")
    private String carModelName;
    @NotNull(message = "car.pointName.notNull")
    @NotEmpty(message = "car.pointName.notEmpty")
    @Size(min = 3, max = 50, message = "car.pointName.size")
    private String pointName;
    @NotNull(message = "car.transporterUsername.notNull")
    @NotEmpty(message = "car.transporterUsername.notEmpty")
    @Size(min = 3, max = 50, message = "car.transporterUsername.size")
    private String transporterUsername;
    @NotNull(message = "car.cost.notNull")
    @Positive(message = "car.cost.notPositive")
    private Double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getTransporterUsername() {
        return transporterUsername;
    }

    public void setTransporterUsername(String transporterUsername) {
        this.transporterUsername = transporterUsername;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
