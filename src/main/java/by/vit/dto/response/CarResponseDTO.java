package by.vit.dto.response;

import by.vit.dto.CarModelDTO;
import by.vit.dto.PointDTO;

/**
 * DTO for response entity Car
 */
public class CarResponseDTO {

    private Long id;
    private CarModelDTO carModel;
    private PointDTO point;
    private UserResponseDTO transporter;
    private Double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarModelDTO getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDTO carModel) {
        this.carModel = carModel;
    }

    public PointDTO getPoint() {
        return point;
    }

    public void setPoint(PointDTO point) {
        this.point = point;
    }

    public UserResponseDTO getTransporter() {
        return transporter;
    }

    public void setTransporter(UserResponseDTO transporter) {
        this.transporter = transporter;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
