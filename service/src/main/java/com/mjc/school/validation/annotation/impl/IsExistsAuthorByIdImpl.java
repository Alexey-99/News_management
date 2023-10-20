package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.validation.annotation.IsExistsAuthorById;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
public class IsExistsAuthorByIdImpl implements ConstraintValidator<IsExistsAuthorById, Long> {
    private static final Logger log = LogManager.getLogger();
    private final AuthorRepository authorRepository;

    @Override
    public boolean isValid(Long authorId, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (authorRepository.existsById(authorId)) {
            log.log(INFO, "Correct entered author ID: " + authorId);
            result = true;
        } else {
            log.log(WARN, "Not found objects with author ID: " + authorId);
        }
        return result;
    }
}
