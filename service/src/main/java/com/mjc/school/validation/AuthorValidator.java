package com.mjc.school.validation;

import com.mjc.school.entity.Author;
import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.AuthorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

/**
 * The type Author validator.
 */
@Component
public class AuthorValidator extends Validator {
    private static final Logger log = LogManager.getLogger();
    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 3;
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Validate author.
     *
     * @param author the author
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validate(Author author)
            throws IncorrectParameterException, RepositoryException {
        return validateName(author.getName()) && isExistsAuthorWithName(author.getName());
    }

    /**
     * Validate author name.
     *
     * @param name the name
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     * @throws RepositoryException         the repository exception
     */
    public boolean validateName(String name)
            throws IncorrectParameterException, RepositoryException {
        if (name != null &&
                (name.length() >= MIN_LENGTH_NAME &&
                        name.length() <= MAX_LENGTH_NAME)) {
            log.log(INFO, "Correct entered author name:" + name);
            return true;
        } else {
            log.log(WARN, "Incorrect entered author name: " + name);
            throw new IncorrectParameterException(BAD_AUTHOR_NAME);
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
    public boolean isExistsAuthorWithName(String name)
            throws IncorrectParameterException, RepositoryException {
        if (!authorRepository.isExistsAuthorWithName(name)) {
            log.log(INFO, "Correct entered author name:" + name);
            return true;
        } else {
            log.log(WARN, "Author with name '" + name + "' exists.");
            throw new IncorrectParameterException(BAD_PARAMETER_AUTHOR_NAME_EXISTS);
        }
    }
}