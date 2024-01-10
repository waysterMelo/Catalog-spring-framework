package com.wayster.catalog.services.validation;

import com.wayster.catalog.dto.UserUpdateDto;
import com.wayster.catalog.entity.User;
import com.wayster.catalog.repositories.UserRepository;
import com.wayster.catalog.resources.exceptions.FieldMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidation implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void initialize(UserUpdateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateDto userUpdateDto, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessages> lista = new ArrayList<>();

        @SuppressWarnings("unchecked")
         var uriVars = (Map<String, String>) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
          long userId = Long.parseLong(uriVars.get("id"));

        User user = userRepository.findByEmail(userUpdateDto.getEmail());
        if (user != null && userId != user.getId()) {
            lista.add(new FieldMessages("email", "EMAIL JA CADASTRADO"));
        }


        for (FieldMessages fieldMessages : lista){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessages.getMessage()).addPropertyNode(fieldMessages.getFieldName()).addConstraintViolation();
        }
        return lista.isEmpty();
    }
}
