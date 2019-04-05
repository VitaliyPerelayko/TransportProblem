package by.vit.model;


import javax.persistence.*;
import java.util.Set;

/**
 * Class for the entity Role. It's role table in database
 */
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "role")
    Set<User> user;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
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

    public void setName(String nane) {
        this.name = nane;
    }

    public Set<User> getUser() {
        return user;
    }
}
