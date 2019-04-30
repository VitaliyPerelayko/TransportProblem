package by.vit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * DTO for request registration entity User
 */
public class UserRegistrationRequestDTO {

    private Long id;

    @Size(min = 3, max = 50, message = "{user.name.size}")
    private String name;

    @Size(min = 3, max = 50, message = "{user.surname.size}")
    private String surname;

    @Size(min = 3, max = 15, message = "{user.phone.size}")
    private String phone;

    @Email(message = "{user.eMail}")
    private String eMail;

    @NotNull(message = "{user.roles.notNull}")
    private Set<
            @NotNull(message = "{user.role.notNull}")
            @NotEmpty(message = "{user.role.notEmpty}")
            @Size(min = 3, max = 50, message = "{user.role.size}")
                    String> roles;

    @NotNull(message = "{user.username.notNull}")
    @NotEmpty(message = "{user.username.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.name.size}")
    private String username;

    @NotNull(message = "{user.password.notNull}")
    @NotEmpty(message = "{user.password.notEmpty}")
    @Size(min = 3, max = 100, message = "{user.password.size}")
    private String password;

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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
}