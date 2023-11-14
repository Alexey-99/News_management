package com.mjc.school.validation.annotation;

import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.impl.IsNotExistsUserByLoginImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsNotExistsUserByLoginImplTest {
    @InjectMocks
    private IsNotExistsUserByLoginImpl isNotExistsUserByLogin;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Test
    void isValid_when_existsUserByLogin() {
        String userLogin = "login_test";
        when(userRepository.notExistsByLogin(userLogin)).thenReturn(false);
        boolean actualResult = isNotExistsUserByLogin.isValid(userLogin, constraintValidatorContext);
        assertFalse(actualResult);
    }

    @Test
    void isValid_when_notExistsUserByLogin() {
        String userLogin = "login_test";
        when(userRepository.notExistsByLogin(userLogin)).thenReturn(true);
        boolean actualResult = isNotExistsUserByLogin.isValid(userLogin, constraintValidatorContext);
        assertTrue(actualResult);
    }
}