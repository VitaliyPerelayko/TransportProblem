package by.vit.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Class for the entity Car. It's transporter_car table in database
 */
@Entity
@Table(name = "transporter_car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_model_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "{car.carModelId.notNull}")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "point_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "{car.pointId.notNull}")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "transporter_id", referencedColumnName = "user_id", nullable = false)
    @NotNull(message = "{car.transporterId.notNull}")
    private Transporter transporter;

    @Column(name = "cost_mult", nullable = false)
    @NotNull(message = "{car.cost.notNull}")
    @Positive(message = "{car.cost.notPositive}")
    private Double cost;

    public Car(CarModel carModel, Point point, Transporter transporter, Double cost) {
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

    public Transporter getTransporter() {
        return transporter;
    }

    public void setTransporter(Transporter transporter) {
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
}
