package by.vit.pojo.solution;


import by.vit.model.Point;

import java.math.BigDecimal;

/**
 * Class contains information about route of car:
 * point (the delivery points)
 * mass (the weight of cargo in tonnes)
 * сщые (the cost of delivery)
 */
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
