package by.vit.model.solution;


import by.vit.model.Point;

import java.math.BigDecimal;

public class Route {
    private Point pointFinish;
    private Double mass;
    private BigDecimal cost;

    public Point getPointFinish() {
        return pointFinish;
    }

    public void setPointFinish(Point pointFinish) {
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
