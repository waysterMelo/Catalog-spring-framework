package com.wayster.catalog.dto;

import com.wayster.catalog.entity.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String authority;

    public RoleDto() {
    }

    public RoleDto(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDto(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }
}
