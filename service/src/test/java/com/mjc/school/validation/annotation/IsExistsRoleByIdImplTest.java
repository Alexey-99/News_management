package com.mjc.school.validation.annotation;

import com.mjc.school.repository.RoleRepository;
import com.mjc.school.validation.annotation.impl.IsExistsRoleByIdImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsExistsRoleByIdImplTest {
    @InjectMocks
    private IsExistsRoleByIdImpl isExistsRoleById;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void isValid_when_foundRole() {
        long roleId = 1L;
        when(roleRepository.existsById(roleId)).thenReturn(true);
        boolean actualResult = isExistsRoleById.isValid(roleId, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notFoundRole() {
        long roleId = 1L;
        when(roleRepository.existsById(roleId)).thenReturn(false);
        boolean actualResult = isExistsRoleById.isValid(roleId, constraintValidatorContext);
        assertFalse(actualResult);
    }
}