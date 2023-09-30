package com.mjc.school.validation.ext;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.author.AuthorRepository;
import com.mjc.school.validation.Validator;
import com.mjc.school.validation.dto.AuthorDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_AUTHOR_NAME;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_AUTHOR_NAME_EXISTS;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Component
public class AuthorValidator extends Validator<AuthorDTO> {
    private static final Logger log = LogManager.getLogger();
    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 3;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public boolean validate(AuthorDTO authorDTO)
            throws IncorrectParameterException, RepositoryException {
        return validateName(authorDTO.getName())
                && isExistsAuthorWithName(authorDTO.getName());
    }

    public boolean validateName(String name)
            throws IncorrectParameterException {
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

    private boolean isExistsAuthorWithName(String name)
            throws IncorrectParameterException, RepositoryException {
        if (!authorRepository.isExistsAuthorWithName(name)) {
            log.log(INFO, "Correct entered author name:" + name);
            return true;
        } else {
            log.log(WARN, "Author with entered name '" + name + "' already exists");
            throw new IncorrectParameterException(BAD_PARAMETER_AUTHOR_NAME_EXISTS);
        }
    }
}