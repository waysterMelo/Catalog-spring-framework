package com.wayster.catalog.dto;

import com.wayster.catalog.entity.Role;
import com.wayster.catalog.entity.User;
import lombok.*;

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
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    Set<RoleDto> roles = new HashSet<>();

    public UserDto(User userEntity) {
        id = userEntity.getId();
        firstName = userEntity.getFirst_name();
        lastName = userEntity.getLast_name();
        email = userEntity.getEmail();
        userEntity.getRoles().forEach(role -> this.roles.add(new RoleDto(role)));
    }



}
