package com.phuoc.carRental.common.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CCCDValidator.class)
public @interface CCCDConstraint {
    String message() default "INVALID_CCCD";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
