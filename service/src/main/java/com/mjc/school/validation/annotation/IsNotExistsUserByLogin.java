package com.mjc.school.validation.annotation;

import com.mjc.school.validation.annotation.impl.IsNotExistsUserByLoginImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsNotExistsUserByLoginImpl.class)
@Documented
public @interface IsNotExistsUserByLogin {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}