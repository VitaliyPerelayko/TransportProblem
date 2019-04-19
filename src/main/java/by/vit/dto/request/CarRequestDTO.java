package by.vit.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO for request entity Car
 */
public class CarRequestDTO {

    private Long id;
    @NotNull(message = "{car.carModelId.notNull}")
    private Long carModelId;
    @NotNull(message = "{car.pointId.notNull}")
    private Long pointId;
    @NotNull(message = "{car.transporterId.notNull}")
    private Long transporterId;
    @NotNull(message = "{car.cost.notNull}")
    @Positive(message = "{car.cost.notPositive}")
    private Double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(Long carModelId) {
        this.carModelId = carModelId;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
