package by.vit.dto.response;

import by.vit.dto.PointDTO;

/**
 * DTO for response entity Route
 */
public class RouteResponseDTO {

    private Double mass;
    private Double cost;
    private PointDTO point;

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public PointDTO getPoint() {
        return point;
    }

    public void setPoint(PointDTO point) {
        this.point = point;
    }
}
