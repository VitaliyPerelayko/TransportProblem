package by.vit.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for request entity User
 */
public class UserRequestDTO {

    private Long id;

    @NotNull(message = "{user.name.notNull}")
    @NotEmpty(message = "{user.name.notEmpty}")
    @Size(min = 3, max = 50, message = "{user.name.size}")
    private String name;

    @Size(min = 3, max = 50, message = "{user.surname.size}")
    private String surname;

    private String phone;

    @Email(message = "{user.eMail}")
    private String eMail;

    @NotNull(message = "{user.roleId.notNull}")
    private Long roleId;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
