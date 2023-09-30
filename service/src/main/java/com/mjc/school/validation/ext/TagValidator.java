package com.mjc.school.validation.ext;

import com.mjc.school.exception.IncorrectParameterException;
import com.mjc.school.exception.RepositoryException;
import com.mjc.school.repository.tag.TagRepository;
import com.mjc.school.validation.Validator;
import com.mjc.school.validation.dto.TagDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_PARAMETER_TAF_NAME_EXISTS;
import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_TAG_NAME;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Component
public class TagValidator extends Validator<TagDTO> {
    private static final Logger log = LogManager.getLogger();
    private static final int MAX_LENGTH_NAME = 15;
    private static final int MIN_LENGTH_NAME = 3;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public boolean validate(TagDTO tagDTO)
            throws IncorrectParameterException, RepositoryException {
        return validateName(tagDTO.getName()) &&
                isExistsAuthorWithName(tagDTO.getName());
    }

    public boolean validateName(String name) throws IncorrectParameterException {
        if (name != null &&
                (name.length() >= MIN_LENGTH_NAME &&
                        name.length() <= MAX_LENGTH_NAME)) {
            log.log(INFO, "Correct entered tag name:" + name);
            return true;
        } else {
            log.log(WARN, "Incorrect entered tag name: " + name);
            throw new IncorrectParameterException(BAD_TAG_NAME);
        }
    }

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