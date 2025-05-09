package com.sidn.metruyenchu.feedbackservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {BirthDateValidator.class})
public @interface BirthDateConstraint {
    String message() default "Invalid Birth Date";

    int min();


    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
