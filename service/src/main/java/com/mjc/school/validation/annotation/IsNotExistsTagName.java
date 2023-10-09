package com.mjc.school.validation.annotation;

import com.mjc.school.validation.annotation.impl.IsNotExistsTagNameImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_TAF_NAME_EXISTS;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsNotExistsTagNameImpl.class)
@Documented
public @interface IsNotExistsTagName {
    String message() default BAD_PARAMETER_TAF_NAME_EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}