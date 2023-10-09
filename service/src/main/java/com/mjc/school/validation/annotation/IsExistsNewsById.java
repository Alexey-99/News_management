package com.mjc.school.validation.annotation;

import com.mjc.school.validation.annotation.impl.IsExistsNewsByIdImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_COMMENT_NEWS_ID;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsExistsNewsByIdImpl.class)
@Documented
public @interface IsExistsNewsById {
    String message() default NO_ENTITY_WITH_COMMENT_NEWS_ID;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}