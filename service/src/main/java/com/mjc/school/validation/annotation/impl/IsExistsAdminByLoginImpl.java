package com.mjc.school.validation.annotation.impl;

import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.IsExistsAdminByLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsAdminByLoginImpl implements ConstraintValidator<IsExistsAdminByLogin, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String adminLogin, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        Optional<User> optionalUser = userRepository.findByLogin(adminLogin);
        if (optionalUser.isPresent()) {
            log.log(DEBUG, "Found user with login " + adminLogin);
            User user = optionalUser.get();
            if (user.getRole().getRole().name().equals(User.UserRole.ROLE_ADMIN.name())) {
                log.log(DEBUG, "Exists administrator with login: " + adminLogin);
                result = true;
            } else {
                log.log(WARN, "Not Found administrator by login: " + adminLogin);
            }
        } else {
            log.log(WARN, "Not found user with login " + adminLogin);
        }
        return result;

    }
}