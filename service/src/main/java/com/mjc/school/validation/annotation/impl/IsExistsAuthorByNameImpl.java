package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.validation.annotation.IsExistsAuthorByName;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsAuthorByNameImpl implements ConstraintValidator<IsExistsAuthorByName, String> {
    private final AuthorRepository authorRepository;

    @Override
    public boolean isValid(String authorName, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (authorRepository.existsByName(authorName)) {
            log.log(DEBUG, "Correct entered author name: " + authorName);
            result = true;
        } else {
            log.log(WARN, "Not found author by name: " + authorName);
        }
        return result;
    }
}