package by.vit.dto.response;

import by.vit.dto.RoleDTO;

import java.util.Set;

/**
 * DTO for response entity User
 */
public class UserResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String eMail;
    private Set<RoleDTO> role;
    private String username;


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

    public Set<RoleDTO> getRoles() {
        return role;
    }

    public void setRoles(Set<RoleDTO> role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
