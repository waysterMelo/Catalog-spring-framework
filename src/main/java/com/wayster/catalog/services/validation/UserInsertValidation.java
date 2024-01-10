package com.wayster.catalog.services.validation;

import com.wayster.catalog.dto.UserInsertDto;
import com.wayster.catalog.entity.User;
import com.wayster.catalog.repositories.UserRepository;
import com.wayster.catalog.resources.exceptions.FieldMessages;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidation implements ConstraintValidator<UserInsertValid, UserInsertDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDto userInsertDto, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessages> lista = new ArrayList<>();


        User user = userRepository.findByEmail(userInsertDto.getEmail());
        if (user != null) {
            lista.add(new FieldMessages("email", "EMAIL JA CADASTRADO"));
        }


        for (FieldMessages fieldMessages : lista){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessages.getMessage()).addPropertyNode(fieldMessages.getFieldName()).addConstraintViolation();
        }
        return lista.isEmpty();
    }
}
