package com.mjc.school.validation.annotation;

import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.impl.IsExistsUserByLoginImpl;
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
class IsExistsUserByLoginImplTest {
    @InjectMocks
    private IsExistsUserByLoginImpl isExistsUserByLogin;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Test
    void isValid_when_existsUserByLogin() {
        String userLogin = "login_test";
        when(userRepository.existsByLogin(userLogin)).thenReturn(true);
        boolean actualResult = isExistsUserByLogin.isValid(userLogin, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notExistsUserByLogin() {
        String userLogin = "login_test";
        when(userRepository.existsByLogin(userLogin)).thenReturn(false);
        boolean actualResult = isExistsUserByLogin.isValid(userLogin, constraintValidatorContext);
        assertFalse(actualResult);
    }
}