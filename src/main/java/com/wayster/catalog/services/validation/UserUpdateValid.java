package com.wayster.catalog.services.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserUpdateValidation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserUpdateValid {

    String message() default "VALIDATION ERROR";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};





}
