package by.vit.model;


import javax.persistence.*;
import java.util.Set;

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

    public String getName() {
        return name;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String nane) {
        this.name = nane;
    }
}
