package com.wayster.catalog.dto;

import com.wayster.catalog.services.validation.UserUpdateValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@UserUpdateValid
public class UserUpdateDto extends UserDto{
    private static final long serialVersionUID = 1L;



}
