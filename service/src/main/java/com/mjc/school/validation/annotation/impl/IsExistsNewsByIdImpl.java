package com.mjc.school.validation.annotation.impl;

import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.impl.news.NewsRepository;
import com.mjc.school.validation.annotation.IsExistsNewsById;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

public class IsExistsNewsByIdImpl
        implements ConstraintValidator<IsExistsNewsById, Long> {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public boolean isValid(Long newsId,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (newsRepository.findById(newsId) != null) {
            log.log(INFO, "Correct entered comment news id: " + newsId);
            result = true;
        } else {
            log.log(WARN, "Not found objects with comment news ID: " + newsId);
        }
        return result;
    }
}