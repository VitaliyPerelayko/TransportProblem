package by.vit.model.solution;

import by.vit.model.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Class for the entity Route. It's route table in database
 */
@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "route.mass.notNull")
    @Positive(message = "route.mass.positive")
    private Double mass;

    @Column(nullable = false)
    @NotNull(message = "route.cost.notNull")
    @Positive(message = "route.cost.positive")
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "route.point.notNull")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "solution_has_transporter_car_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "route.solutionCar.notNull")
    private SolutionCar solutionCar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public SolutionCar getSolutionCar() {
        return solutionCar;
    }

    public void setSolutionCar(SolutionCar solutionCar) {
        this.solutionCar = solutionCar;
    }
}
