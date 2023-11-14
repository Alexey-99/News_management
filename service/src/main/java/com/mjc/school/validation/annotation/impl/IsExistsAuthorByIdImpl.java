package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.validation.annotation.IsExistsAuthorById;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsAuthorByIdImpl implements ConstraintValidator<IsExistsAuthorById, Long> {
    private final AuthorRepository authorRepository;

    @Override
    public boolean isValid(Long authorId, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (authorRepository.existsById(authorId)) {
            log.log(DEBUG, "Correct entered author ID: " + authorId);
            result = true;
        } else {
            log.log(WARN, "Not found objects with author ID: " + authorId);
        }
        return result;
    }
}
