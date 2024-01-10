package com.mjc.school.validation.annotation;

import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.impl.IsExistsUserByIdImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsExistsUserByIdImplTest {
    @InjectMocks
    private IsExistsUserByIdImpl isExistsUserById;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Test
    void isValid_when_existsUserByLogin() {
        long userId = 1;
        when(userRepository.existsById(anyLong())).thenReturn(true);
        boolean actualResult = isExistsUserById.isValid(userId, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notExistsUserByLogin() {
        long userId = 1;
        when(userRepository.existsById(anyLong())).thenReturn(false);
        boolean actualResult = isExistsUserById.isValid(userId, constraintValidatorContext);
        assertFalse(actualResult);
    }
}