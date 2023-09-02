package com.mjc.school.validation;

import com.mjc.school.exception.IncorrectParameterException;

import static com.mjc.school.exception.ExceptionIncorrectParameterMessageCode.BAD_ID;

/**
 * The type Validator.
 */
public abstract class Validator {
    private static final int MIN_ID = 1;

    /**
     * Validate ID.
     *
     * @param id the id
     * @return the boolean
     * @throws IncorrectParameterException the incorrect parameter exception
     */
    public boolean validateId(long id) throws IncorrectParameterException {
        if (id >= MIN_ID) {
            return true;
        } else {
            throw new IncorrectParameterException(BAD_ID);
        }
    }
}