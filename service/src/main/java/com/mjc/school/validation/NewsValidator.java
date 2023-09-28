package com.mjc.school.validation;

import com.mjc.school.entity.News;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.repository.news.NewsRepository;
import com.mjc.school.validation.dto.NewsDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_AUTHOR_ID;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_NEWS_TITLE;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_NEWS_TITLE_EXISTS;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_AUTHORS_WITH_ID;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Component
public class NewsValidator extends Validator {
    private static final Logger log = LogManager.getLogger();
    private static final int MAX_LENGTH_TITLE = 30;
    private static final int MIN_LENGTH_TITLE = 5;
    private static final int MAX_LENGTH_CONTENT = 255;
    private static final int MIN_LENGTH_CONTENT = 5;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private NewsRepository newsRepository;

    public boolean validate(NewsDTO newsDTO)
            throws IncorrectParameterException, RepositoryException {
        return validateTitle(newsDTO.getTitle()) &&
                isExistsNewsWithTitle(newsDTO.getTitle()) &&
                validateContent(newsDTO.getContent()) &&
                validateAuthorId(newsDTO.getAuthor().getId());
    }

    public boolean validateTitle(String title)
            throws IncorrectParameterException {
        if (title != null &&
                (title.length() >= MIN_LENGTH_TITLE &&
                        title.length() <= MAX_LENGTH_TITLE)) {
            log.log(INFO, "Correct entered news title: " + title);
            return true;
        } else {
            log.log(WARN, "Incorrect entered news title: " + title);
            throw new IncorrectParameterException(BAD_NEWS_TITLE);
        }
    }

    private boolean isExistsNewsWithTitle(String title)
            throws IncorrectParameterException, RepositoryException {
        if (!newsRepository.isExistsNewsWithTitle(title)) {
            log.log(INFO, "Correct entered news title: " + title);
            return true;
        } else {
            log.log(WARN, "News with title '" + title + "' exists.");
            throw new IncorrectParameterException(BAD_PARAMETER_NEWS_TITLE_EXISTS);
        }
    }

    public boolean validateContent(String content)
            throws IncorrectParameterException {
        if (content != null &&
                (content.length() >= MIN_LENGTH_CONTENT &&
                        content.length() <= MAX_LENGTH_CONTENT)) {
            log.log(INFO, "Correct entered news content: " + content);
            return true;
        } else {
            log.log(WARN, "Incorrect entered news content: " + content);
            throw new IncorrectParameterException(BAD_NEWS_CONTENT);
        }
    }

    public boolean validateAuthorId(long authorId)
            throws IncorrectParameterException, RepositoryException {
        if (super.validateId(authorId)) {
            if (authorRepository.findById(authorId) != null) {
                log.log(INFO, "Correct entered news author id: " + authorId);
                return true;
            } else {
                log.log(WARN, "Not found authors with ID: " + authorId);
                throw new IncorrectParameterException(NO_AUTHORS_WITH_ID);
            }
        } else {
            log.log(WARN, "Incorrect entered news author id: " + authorId);
            throw new IncorrectParameterException(BAD_NEWS_AUTHOR_ID);
        }
    }
}