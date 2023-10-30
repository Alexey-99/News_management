package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.IsNotExistsUserByLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsNotExistsUserByLoginImpl implements ConstraintValidator<IsNotExistsUserByLogin, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (userRepository.notExistsByLogin(login)) {
            log.log(INFO, "Not exists user with login " + login);
            result = true;
        } else {
            log.log(WARN, "Exists user with login " + login);
        }
        return result;
    }
}