package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.NewsRepository;
import com.mjc.school.validation.annotation.IsExistsNewsById;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsNewsByIdImpl implements ConstraintValidator<IsExistsNewsById, Long> {
    private final NewsRepository newsRepository;

    @Override
    public boolean isValid(Long newsId, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (newsRepository.existsById(newsId)) {
            log.log(INFO, "Correct entered comment news id: " + newsId);
            result = true;
        } else {
            log.log(WARN, "Not found objects with comment news ID: " + newsId);
        }
        return result;
    }
}