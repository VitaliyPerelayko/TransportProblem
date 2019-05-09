package by.vit.model;


import by.vit.model.solution.Solution;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Class for the entity User. It's users table in database
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50, message = "user.name.size")
    private String name;

    @Size(min = 3, max = 50, message = "user.surname.size")
    private String surname;

    @Size(min = 3, max = 15, message = "user.phone.size")
    private String phone;

    @Column(name = "e_mail")
    @Email(message = "user.eMail")
    private String eMail;

    @Column(nullable = false, unique = true)
    @NotNull(message = "user.username.notNull")
    @NotEmpty(message = "user.username.notEmpty")
    @Size(min = 3, max = 50, message = "user.username.size")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "user.password.notNull")
    @NotEmpty(message = "user.password.notEmpty")
    @Size(min = 3, max = 100, message = "user.password.size")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_has_role",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotNull(message = "user.roles.notNull")
    @NotEmpty(message = "user.roles.notEmpty" )
    private Set<
            @NotNull(message = "user.role.notNull")
                    Role> roles;

    @OneToMany(mappedBy = "transporter")
    private Set<Car> cars;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Solution> solutions;

    public User(String name, String surname, String phone, String eMail, Set<Role> role) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eMail = eMail;
        this.roles = role;
    }

    public User() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Solution> getSolutions() {
        return solutions;
    }

    public Set<Car> getCars() {
        return cars;
    }
}
