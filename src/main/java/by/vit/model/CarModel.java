package by.vit.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Class for the entity CarModel. It's car_model table in database
 */
@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carModel")
    private Set<Car> cars;

    @Column(nullable = false, unique = true)
    @NotNull(message = "{carModel.name.notNull}")
    @NotEmpty(message = "{carModel.name.notEmpty}")
    @Size(min = 1, max = 50, message = "{carModel.name.size}")
    private String name;

    @NotNull(message = "{carModel.tonnage.notNull}")
    @Positive(message = "{carModel.tonnage.notPositive}")
    private Double tonnage;

    @NotNull(message = "{carModel.space.notNull}")
    @Positive(message = "{carModel.space.notPositive}")
    private Double space;

    public CarModel(String name, Double tonnage, Double space) {
        this.name = name;
        this.tonnage = tonnage;
        this.space = space;
    }

    public CarModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTonnage() {
        return tonnage;
    }

    public void setTonnage(Double tonnage) {
        this.tonnage = tonnage;
    }

    public Double getSpace() {
        return space;
    }

    public void setSpace(Double space) {
        this.space = space;
    }
}
