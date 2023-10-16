package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.validation.annotation.IsNotExistsNewsTitle;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.WARN;

@RequiredArgsConstructor
public class IsNotExistsNewsTitleImpl
        implements ConstraintValidator<IsNotExistsNewsTitle, String> {
    private static final Logger log = LogManager.getLogger();
    private final NewsRepository newsRepository;

    @Override
    public boolean isValid(String title,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (newsRepository.findNewsByTitle(title).isEmpty()) {
            result = true;
        } else {
            log.log(WARN, "News with title '" + title + "' exists.");
        }
        return result;
    }
}