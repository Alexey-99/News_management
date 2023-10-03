package com.mjc.school.validation.ext.impl;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.validation.ext.IsNotExistsNewsTitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

public class IsNotExistsNewsTitleImpl
        implements ConstraintValidator<IsNotExistsNewsTitle, String> {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public boolean isValid(String title,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        try {
            if (!newsRepository.isExistsNewsWithTitle(title)) {
                result = true;
            } else {
                log.log(WARN, "News with title '" + title + "' exists.");
            }
        } catch (RepositoryException e) {
            log.log(ERROR, e.getMessage());
        }
        return result;

    }
}
