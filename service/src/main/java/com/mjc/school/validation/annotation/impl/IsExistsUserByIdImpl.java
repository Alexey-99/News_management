package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.IsExistsUserById;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsUserByIdImpl implements ConstraintValidator<IsExistsUserById, Long> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (userRepository.existsById(userId)) {
            log.log(DEBUG, "Correct entered user ID: {}", userId);
            result = true;
        } else {
            log.log(WARN, "Not found user with ID: {}", userId);
        }
        return result;
    }
}
