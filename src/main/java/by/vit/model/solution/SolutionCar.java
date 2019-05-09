package by.vit.model.solution;

import by.vit.model.Car;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Class for the entity SolutionCar. It's solution_has_transporter_car table in database
 */
@Entity
@Table(name = "solution_has_transporter_car")
public class SolutionCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solution_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "solutionCar.solution.notNull")
    private Solution solution;

    @ManyToOne
    @JoinColumn(name = "transporter_car_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "solutionCar.car.notNull")
    private Car car;

    @OneToMany(mappedBy = "solutionCar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Route> routes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<Route> getRoutes() {
        return routes;
    }
}
