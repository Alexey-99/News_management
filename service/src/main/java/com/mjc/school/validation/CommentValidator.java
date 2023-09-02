package com.mjc.school.validation;

import com.mjc.school.entity.Comment;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.news.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_COMMENT_CONTENT;
import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_COMMENT_NEWS_ID;

/**
 * The type Comment validator.
 */
public class CommentValidator extends Validator {
    private static final int MAX_LENGTH_NAME = 255;
    private static final int MIN_LENGTH_NAME = 3;
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
    public boolean validate(Comment comment) throws IncorrectParameterException, RepositoryException {
        return validateContent(comment.getContent()) && validateNewsId(comment.getNewsId());
    }

    /**
     * Validate comment content.
     *
     * @param content the content
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean validateContent(String content) throws IncorrectParameterException {
        if (content != null &&
                (content.length() >= MIN_LENGTH_NAME && content.length() <= MAX_LENGTH_NAME)) {
            return true;
        } else {
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
    public boolean validateNewsId(long newsId) throws IncorrectParameterException, RepositoryException {
        if (validateId(newsId) && newsRepository.findNewsById(newsId) != null) {
            return true;
        } else {
            throw new IncorrectParameterException(BAD_COMMENT_NEWS_ID);
        }
    }
}