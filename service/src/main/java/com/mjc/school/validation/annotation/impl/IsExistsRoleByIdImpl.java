package com.mjc.school.validation.annotation.impl;

import com.mjc.school.repository.RoleRepository;
import com.mjc.school.validation.annotation.IsExistsRoleById;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
public class IsExistsRoleByIdImpl implements ConstraintValidator<IsExistsRoleById, Long> {
    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(Long roleId, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (roleRepository.existsById(roleId)) {
            log.log(INFO, "Correct entered role id: " + roleId);
            result = true;
        } else {
            log.log(WARN, "Not found role by ID: " + roleId);
        }
        return result;
    }
}