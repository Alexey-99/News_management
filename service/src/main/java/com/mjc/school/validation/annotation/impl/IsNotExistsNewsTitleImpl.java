package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.impl.news.NewsRepository;
import com.mjc.school.validation.annotation.IsNotExistsNewsTitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNotExistsNewsTitleImpl
        implements ConstraintValidator<IsNotExistsNewsTitle, String> {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public boolean isValid(String title,
                           ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
//        if (!newsRepository.isExistsNewsWithTitle(title)) { // TODO newsRepository.isExistsNewsWithTitle(title)
//            result = true;
//        } else {
//            log.log(WARN, "News with title '" + title + "' exists.");
//        }
        return result;
    }
}
