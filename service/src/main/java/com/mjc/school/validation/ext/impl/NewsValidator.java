package com.mjc.school.validation.ext.impl;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.validation.ext.IsNotExistsNewsTitle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_AUTHORS_WITH_ID;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Component
public class NewsValidator implements ConstraintValidator<IsNotExistsNewsTitle, String> {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsRepository newsRepository;

//    public boolean validate(NewsDTO newsDTO)
//            throws IncorrectParameterException, RepositoryException {
//        return validateAuthorId(newsDTO.getAuthor().getId());
////                && isNotExistsNewsWithTitle(newsDTO.getTitle())
//
//    }

//    private boolean isNotExistsNewsWithTitle(String title)
//            throws IncorrectParameterException, RepositoryException {
//        if (!newsRepository.isExistsNewsWithTitle(title)) {
//            log.log(INFO, "Correct entered news title: " + title);
//            return true;
//        } else {
//            log.log(WARN, "News with title '" + title + "' exists.");
//            throw new IncorrectParameterException(BAD_PARAMETER_NEWS_TITLE_EXISTS);
//        }
//    }

    public boolean validateAuthorId(long authorId)
            throws IncorrectParameterException, RepositoryException {
        if (authorRepository.findById(authorId) != null) {
            log.log(INFO, "Correct entered news author id: " + authorId);
            return true;
        } else {
            log.log(WARN, "Not found authors with ID: " + authorId);
            throw new IncorrectParameterException(NO_AUTHORS_WITH_ID);
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}