package by.vit.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "point")
    private Set<Car> cars;

}
