package by.vit.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "transporter")
@PrimaryKeyJoinColumn(name = "user_id")
public class Transporter extends User {


    private String license;

    @OneToMany(mappedBy = "transporter")
    private Set<Car> cars;

    public Transporter(User user, String license) {
        super(user.getName(), user.getSurname(), user.getPhone(), user.geteMail(),user.getRole());
        this.license = license;
    }

    public Transporter() {
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Set<Car> getCars() {
        return cars;
    }
}
