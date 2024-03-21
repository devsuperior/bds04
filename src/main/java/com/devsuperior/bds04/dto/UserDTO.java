package com.devsuperior.bds04.dto;

import com.devsuperior.bds04.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserDTO implements Serializable {

    private Long id;
    @Email(message = "Informe um e-mail válido")
    private String email;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {

    }

    public UserDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.email = email;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

}
