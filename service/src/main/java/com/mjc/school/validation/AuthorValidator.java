package com.mjc.school.validation;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.IncorrectParameterException;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_NEWS_TITLE;

/**
 * The type Author validator.
 */
@Component
public class AuthorValidator extends Validator {
    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 3;

    /**
     * Validate author.
     *
     * @param author the author
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean validate(Author author)
            throws IncorrectParameterException {
        return validateName(author.getName());
    }

    /**
     * Validate author name.
     *
     * @param name the author name
     * @return the boolean
     * @throws IncorrectParameterException an exception thrown in case incorrect name
     */
    public boolean validateName(String name) throws IncorrectParameterException {
        if (name != null &&
                (name.length() >= MIN_LENGTH_NAME && name.length() <= MAX_LENGTH_NAME)) {
            return true;
        } else {
            throw new IncorrectParameterException(BAD_NEWS_TITLE);
        }
    }
}