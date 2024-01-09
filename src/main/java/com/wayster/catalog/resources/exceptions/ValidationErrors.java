package com.wayster.catalog.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrors extends StandardError{
    private static final long serialVersionUID = 1L;

    private List<FieldMessages> erros = new ArrayList<>();

    public void addError(String fieldName, String fieldMessage){
        erros.add(new FieldMessages(fieldName, fieldMessage));
    }



}
