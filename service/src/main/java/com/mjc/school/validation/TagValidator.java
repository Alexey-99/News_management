package com.mjc.school.validation;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.IncorrectParameterException;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_TAG_NAME;

/**
 * The type Tag validator.
 */
@Component
public class TagValidator extends Validator {
    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 3;

    /**
     * Validate tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean validate(Tag tag)
            throws IncorrectParameterException {
        return validateName(tag.getName());
    }

    /**
     * Validate tag name.
     *
     * @param name the author name
     * @return the boolean
     * @throws IncorrectParameterException an exception thrown in case incorrect name
     */
    public boolean validateName(String name) throws IncorrectParameterException { // TODO ДОЛЖЕН БЫТЬ УНИКАЛЬНЫМ
        if (name != null &&
                (name.length() >= MIN_LENGTH_NAME && name.length() <= MAX_LENGTH_NAME)) {
            return true;
        } else {
            throw new IncorrectParameterException(BAD_TAG_NAME);
        }
    }
}