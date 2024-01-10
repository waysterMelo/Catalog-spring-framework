package com.wayster.catalog.dto;

import com.wayster.catalog.services.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserInsertValid
public class UserInsertDto extends UserDto{
    private static final long serialVersionUID = 1L;

    private String password;



}
