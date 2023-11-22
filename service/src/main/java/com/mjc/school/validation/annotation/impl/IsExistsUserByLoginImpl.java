package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.IsExistsUserByLogin;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsUserByLoginImpl implements ConstraintValidator<IsExistsUserByLogin, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (userRepository.existsByLogin(login)) {
            log.log(DEBUG, "Exists user with login " + login);
            result = true;
        } else {
            log.log(WARN, "Not found user with login " + login);
        }
        return result;
    }
}