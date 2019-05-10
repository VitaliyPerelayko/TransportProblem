package by.vit.model;

import by.vit.model.solution.SolutionCar;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

/**
 * Class for the entity Car. It's transporter_car table in database
 */
@Entity
@Table(name = "transporter_car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_model_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "car.carModel.notNull")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "car.point.notNull")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "transporter_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "car.transporter.notNull")
    private User transporter;

    @Column(name = "cost_mult", nullable = false)
    @NotNull(message = "car.cost.notNull")
    @Positive(message = "car.cost.notPositive")
    private Double cost;

    @OneToMany(mappedBy = "car")
    private Set<SolutionCar> solutionCars;

    public Car(CarModel carModel, Point point, User transporter, Double cost) {
        this.carModel = carModel;
        this.point = point;
        this.transporter = transporter;
        this.cost = cost;
    }

    public Car() {
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public User getTransporter() {
        return transporter;
    }

    public void setTransporter(User transporter) {
        this.transporter = transporter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Set<SolutionCar> getSolutionCars() {
        return solutionCars;
    }
}
