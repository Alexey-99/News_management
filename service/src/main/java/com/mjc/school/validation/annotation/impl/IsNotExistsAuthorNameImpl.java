package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.validation.annotation.IsNotExistsAuthorName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.WARN;

public class IsNotExistsAuthorNameImpl
        implements ConstraintValidator<IsNotExistsAuthorName, String> {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public boolean isValid(String name,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (authorRepository.isNotExistsAuthorWithName(name)) {
            result = true;
        } else {
            log.log(WARN, "Author with entered name '" + name + "' already exists");
        }
        return result;
    }
}