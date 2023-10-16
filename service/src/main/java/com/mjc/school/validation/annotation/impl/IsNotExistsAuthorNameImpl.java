package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.impl.author.AuthorRepository;
import com.mjc.school.validation.annotation.IsNotExistsAuthorName;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
public class IsNotExistsAuthorNameImpl
        implements ConstraintValidator<IsNotExistsAuthorName, String> {
    private static final Logger log = LogManager.getLogger();
    private final AuthorRepository authorRepository;

    @Override
    public boolean isValid(String name,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (authorRepository.findAuthorByName(name).isEmpty()) {
            result = true;
        } else {
            log.log(WARN, "Author with entered name '" + name + "' already exists");
        }
        return result;
    }
}