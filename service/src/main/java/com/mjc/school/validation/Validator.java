package com.mjc.school.validation;

import com.mjc.school.exception.IncorrectParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;

import static com.mjc.school.exception.code.ExceptionIncorrectParameterMessageCode.BAD_ID;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;

@Validated
public abstract class Validator {
    private static final Logger log = LogManager.getLogger();
    private static final int MIN_ID = 1;

    public boolean validateId( long id) throws IncorrectParameterException {
        if (id >= MIN_ID) {
            log.log(INFO, "Correct entered ID:" + id);
            return true;
        } else {
            log.log(ERROR, "Incorrect entered ID:" + id);
            throw new IncorrectParameterException(BAD_ID);
        }
    }
}