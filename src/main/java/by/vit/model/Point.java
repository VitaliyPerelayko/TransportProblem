package by.vit.model;

import by.vit.model.solution.Route;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Class for the entity Point. It's points table in database
 */
@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "point.name.notNull")
    @NotEmpty(message = "point.name.notEmpty")
    @Size(min = 3, max = 50, message = "point.name.size")
    private String name;

    @OneToMany(mappedBy = "point1")
    private Set<Road> roads1;

    @OneToMany(mappedBy = "point2")
    private Set<Road> roads2;

    @OneToMany(mappedBy = "point", fetch = FetchType.EAGER)
    private Set<Car> cars;

    @OneToMany(mappedBy = "point")
    private Set<Route> routes;

    public Point(String name) {
        this.name = name;
    }

    public Point() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Road> getRoads1() {
        return roads1;
    }

    public void setRoads1(Set<Road> roads1) {
        this.roads1 = roads1;
    }

    public Set<Road> getRoads2() {
        return roads2;
    }

    public void setRoads2(Set<Road> roads2) {
        this.roads2 = roads2;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public Set<Route> getRoutes() {
        return routes;
    }
}
