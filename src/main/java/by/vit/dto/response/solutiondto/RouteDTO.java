package by.vit.dto.response.solutiondto;

import by.vit.dto.PointDTO;

import java.math.BigDecimal;

public class RouteDTO {
    private PointDTO pointFinish;
    private Double mass;
    private BigDecimal cost;

    public PointDTO getPointFinish() {
        return pointFinish;
    }

    public void setPointFinish(PointDTO pointFinish) {
        this.pointFinish = pointFinish;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
