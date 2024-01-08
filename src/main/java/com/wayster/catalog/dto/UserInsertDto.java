package com.wayster.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDto extends UserDto{
    private static final long serialVersionUID = 1L;

    private String password;



}
