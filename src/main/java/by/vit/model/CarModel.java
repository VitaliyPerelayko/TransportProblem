package by.vit.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carModel")
    private Set<Car> cars;

    private String name;
    private Double tonage;
    private Double space;

    public CarModel(String name, Double tonage, Double space) {
        this.name = name;
        this.tonage = tonage;
        this.space = space;
    }

    public CarModel() {
    }

    public Long getId() {
        return id;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public String getName() {
        return name;
    }

    public Double getTonage() {
        return tonage;
    }

    public Double getSpace() {
        return space;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTonage(Double tonage) {
        this.tonage = tonage;
    }

    public void setSpace(Double space) {
        this.space = space;
    }
}
