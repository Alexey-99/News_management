package com.mjc.school.validation;

import com.mjc.school.entity.News;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_NEWS_AUTHOR_ID;
import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_NEWS_CONTENT;

/**
 * The type News validator.
 */
@Component
public class NewsValidator extends Validator {
    private static final int MAX_LENGTH_TITLE = 30;
    private static final int MIN_LENGTH_TITLE = 5;
    private static final int MAX_LENGTH_CONTENT = 255;
    private static final int MIN_LENGTH_CONTENT = 5;
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Validate boolean.
     *
     * @param news the news
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validate(News news)
            throws IncorrectParameterException, RepositoryException {
        return validateTitle(news.getTitle()) &&
                validateContent(news.getContent()) &&
                validateAuthorId(news.getAuthorId());
    }

    /**
     * Validate title boolean.
     *
     * @param title the title
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean validateTitle(String title)
            throws IncorrectParameterException {
        if (title != null &&
                (title.length() >= MIN_LENGTH_TITLE &&
                        title.length() <= MAX_LENGTH_TITLE)) {
            return true;
        } else {
            throw new IncorrectParameterException(BAD_AUTHOR_NAME);
        }
    }

    /**
     * Validate content boolean.
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
            return true;
        } else {
            throw new IncorrectParameterException(BAD_NEWS_CONTENT);
        }
    }

    /**
     * Validate author id boolean.
     *
     * @param authorId the author id
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validateAuthorId(long authorId)
            throws IncorrectParameterException, RepositoryException {
        if (super.validateId(authorId) &&
                authorRepository.findById(authorId) != null) {
            return true;
        } else {
            throw new IncorrectParameterException(BAD_NEWS_AUTHOR_ID);
        }
    }
}