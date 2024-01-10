package com.wayster.catalog.dto;

import com.wayster.catalog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "CAMPO OBRIGATORIO")
    private String firstName;
    private String lastName;
    @Email(message = "INSIRA O EMAIL")
    private String email;
    private String password;

    Set<RoleDto> roles = new HashSet<>();

    public UserDto(User userEntity) {
        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        email = userEntity.getEmail();
        userEntity.getRoles().forEach(role -> this.roles.add(new RoleDto(role)));
    }



}
