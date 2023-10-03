package com.mjc.school.validation.annotation.impl;

import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.validation.annotation.IsNotExistsTagName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

public class IsNotExistsTagNameImpl
        implements ConstraintValidator<IsNotExistsTagName, String> {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private TagRepository tagRepository;

    @Override
    public boolean isValid(String name,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        try {
            if (!tagRepository.isExistsTagWithName(name)) {
                result = true;
            } else {
                log.log(WARN, "Tag with entered name '" + name + "' already exists");
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
        }
        return result;
    }
}