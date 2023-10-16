package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.validation.annotation.IsNotExistsTagName;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
public class IsNotExistsTagNameImpl
        implements ConstraintValidator<IsNotExistsTagName, String> {
    private static final Logger log = LogManager.getLogger();
    private final TagRepository tagRepository;

    @Override
    public boolean isValid(String name,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (tagRepository.findByName(name).isEmpty()) {
            result = true;
        } else {
            log.log(WARN, "Tag with entered name '" + name + "' already exists");
        }
        return result;
    }
}