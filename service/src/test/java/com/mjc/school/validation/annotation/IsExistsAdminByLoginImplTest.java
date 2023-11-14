package com.mjc.school.validation.annotation;

import com.mjc.school.model.user.Role;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.validation.annotation.impl.IsExistsAdminByLoginImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import java.util.Optional;

import static com.mjc.school.model.user.User.UserRole.ROLE_ADMIN;
import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsExistsAdminByLoginImplTest {
    @InjectMocks
    private IsExistsAdminByLoginImpl isExistsAdminByLogin;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void isValid_when_foundUserByLoginWithRoleUser() {
        String userLogin = "login_test";
        when(userRepository.findByLogin(userLogin))
                .thenReturn(Optional.of(
                        User.builder()
                                .id(1)
                                .role(Role.builder()
                                        .id(1)
                                        .role(ROLE_USER)
                                        .build())
                                .build()));
        boolean actualResult = isExistsAdminByLogin.isValid(userLogin, constraintValidatorContext);
        assertFalse(actualResult);
    }

    @Test
    void isValid_when_foundUserByLoginWithRoleAdmin() {
        String userLogin = "login_test";
        when(userRepository.findByLogin(userLogin))
                .thenReturn(Optional.of(
                        User.builder()
                                .id(1)
                                .role(Role.builder()
                                        .id(2)
                                        .role(ROLE_ADMIN)
                                        .build())
                                .build()));
        boolean actualResult = isExistsAdminByLogin.isValid(userLogin, constraintValidatorContext);
        assertTrue(actualResult);
    }

    @Test
    void isValid_when_notFoundUserByLogin() {
        String userLogin = "login_test";
        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());
        boolean actualResult = isExistsAdminByLogin.isValid(userLogin, constraintValidatorContext);
        assertFalse(actualResult);
    }
}