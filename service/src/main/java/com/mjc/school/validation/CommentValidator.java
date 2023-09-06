package com.mjc.school.validation;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.news.NewsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_COMMENT_CONTENT;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_COMMENT_NEWS_ID;
import static com.mjc.school.exception.code.ExceptionServiceMessageCodes.NO_ENTITY_WITH_COMMENT_NEWS_ID;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The type Comment validator.
 */
@Component
public class CommentValidator extends Validator {
    private static final Logger log = LogManager.getLogger();
    private static final int MAX_LENGTH_CONTENT = 255;
    private static final int MIN_LENGTH_CONTENT = 3;
    @Autowired
    private NewsRepository newsRepository;

    /**
     * Validate comment.
     *
     * @param comment the comment
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validate(Comment comment)
            throws IncorrectParameterException, RepositoryException {
        return validateContent(comment.getContent()) &&
                validateNewsId(comment.getNewsId());
    }

    /**
     * Validate comment content.
     *
     * @param content the content
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean validateContent(String content)
            throws IncorrectParameterException {
        if (content != null &&
                (content.length() >= MIN_LENGTH_CONTENT &&
                        content.length() <= MAX_LENGTH_CONTENT)) {
            log.log(INFO, "Correct entered comment content: " + content);
            return true;
        } else {
            log.log(WARN, "Incorrect entered comment content: " + content);
            throw new IncorrectParameterException(BAD_COMMENT_CONTENT);
        }
    }

    /**
     * Validate comment news id.
     *
     * @param newsId the news id
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validateNewsId(long newsId)
            throws IncorrectParameterException, RepositoryException {
        if (validateId(newsId)) {
            if (newsRepository.findNewsById(newsId) != null) {
                log.log(INFO, "Correct entered comment news id: " + newsId);
                return true;
            } else {
                log.log(WARN, "Not found objects with comment news ID: " + newsId);
                throw new IncorrectParameterException(NO_ENTITY_WITH_COMMENT_NEWS_ID);
            }
        } else {
            log.log(WARN, "Incorrect entered comment news id: " + newsId);
            throw new IncorrectParameterException(BAD_COMMENT_NEWS_ID);
        }
    }
}