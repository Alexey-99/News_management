package com.mjc.school.validation;

import com.mjc.school.entity.Tag;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.tag.TagRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_TAF_NAME_EXISTS;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_TAG_NAME;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The type Tag validator.
 */
@Component
public class TagValidator extends Validator {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private TagRepository tagRepository;
    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 3;

    /**
     * Validate tag.
     *
     * @param tag the tag
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validate(Tag tag)
            throws IncorrectParameterException, RepositoryException {
        return validateName(tag.getName()) &&
                isExistsAuthorWithName(tag.getName());
    }

    /**
     * Validate tag name.
     *
     * @param name the author name
     * @return the boolean
     * @throws IncorrectParameterException an exception thrown in case incorrect name
     */
    public boolean validateName(String name) throws IncorrectParameterException {
        if (name != null &&
                (name.length() >= MIN_LENGTH_NAME && name.length() <= MAX_LENGTH_NAME)) {
            log.log(INFO, "Correct entered tag name:" + name);
            return true;
        } else {
            log.log(WARN, "Incorrect entered tag name: " + name);
            throw new IncorrectParameterException(BAD_TAG_NAME);
        }
    }

    /**
     * Is exists author with name.
     *
     * @param name the name
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    private boolean isExistsAuthorWithName(String name)
            throws IncorrectParameterException, RepositoryException {
        if (!tagRepository.isExistsTagWithName(name)) {
            log.log(INFO, "Correct entered tag name:" + name);
            return true;
        } else {
            log.log(WARN, "Tag with entered name '" + name + "' already exists");
            throw new IncorrectParameterException(BAD_PARAMETER_TAF_NAME_EXISTS);
        }
    }
}