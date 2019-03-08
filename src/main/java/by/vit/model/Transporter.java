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

    public Transporter(String name, String surname, String phone, String eMail, Role role, String license, Set<Car> cars) {
        super(name, surname, phone, eMail, role);
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
