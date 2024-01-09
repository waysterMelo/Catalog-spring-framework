package com.wayster.catalog.dto;

import com.wayster.catalog.entity.Role;
import com.wayster.catalog.entity.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
