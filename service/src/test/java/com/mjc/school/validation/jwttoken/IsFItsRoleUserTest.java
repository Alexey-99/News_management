package com.mjc.school.validation.jwttoken;

import com.mjc.school.exception.CustomAccessDeniedException;
import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
import com.mjc.school.validation.impl.JwtTokenValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.mjc.school.model.user.User.UserRole.ROLE_ADMIN;
import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsFItsRoleUserTest {
    @InjectMocks
    private JwtTokenValidatorImpl jwtTokenValidator;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void isFitsRoleUser_when_validationJwtTokenIsNull() {
        ValidationJwtToken validationJwtToken = null;

        CustomAuthenticationException exception = assertThrows(CustomAuthenticationException.class,
                () -> jwtTokenValidator.isFitsRoleUser(validationJwtToken));
        assertEquals("exception.access_without_authorization", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "providerValidationJwtToken_when_throwCustomAuthenticationException")
    void isFitsRoleUser_when_throwCustomAuthenticationException(ValidationJwtToken validationJwtToken) {
        CustomAuthenticationException exception = assertThrows(CustomAuthenticationException.class,
                () -> jwtTokenValidator.isFitsRoleUser(validationJwtToken));
        assertEquals("exception.access_without_authorization", exception.getMessage());
    }

    static List<Arguments> providerValidationJwtToken_when_throwCustomAuthenticationException() {
        return List.of(
                Arguments.of(new ValidationJwtToken(null)),
                Arguments.of(new ValidationJwtToken("token")));
    }

    @Test
    void isFitsRoleUser_when_validationJwtTokenNotNull_and_correctToken_and_foundUserName_and_notFoundRoles() {
        ValidationJwtToken validationJwtToken = new ValidationJwtToken("user");

        String userName = "userName";
        when(jwtTokenUtil.getUserName(anyString())).thenReturn(userName);

        List<String> roles = List.of();
        when(jwtTokenUtil.getRoles(anyString())).thenReturn(roles);

        CustomAccessDeniedException exception = assertThrows(CustomAccessDeniedException.class,
                () -> jwtTokenValidator.isFitsRoleUser(validationJwtToken));
        assertEquals("exception.access_denied", exception.getMessage());
    }

    @Test
    void isFitsRoleUser_when_validationJwtTokenNotNull_and_correctToken_and_foundUserName_and_foundRoleNotCorrect() {
        ValidationJwtToken validationJwtToken = new ValidationJwtToken("user");

        String userName = "userName";
        when(jwtTokenUtil.getUserName(anyString())).thenReturn(userName);

        List<String> roles = List.of("role");
        when(jwtTokenUtil.getRoles(anyString())).thenReturn(roles);

        CustomAccessDeniedException exception = assertThrows(CustomAccessDeniedException.class,
                () -> jwtTokenValidator.isFitsRoleUser(validationJwtToken));
        assertEquals("exception.access_denied", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "providerCorrectRoleList")
    void isFitsRoleUser_when_validationJwtTokenNotNull_and_correctToken_and_foundUserName_and_foundRoleCorrect(List<String> roles)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        ValidationJwtToken validationJwtToken = new ValidationJwtToken("user");

        String userName = "userName";
        when(jwtTokenUtil.getUserName(anyString())).thenReturn(userName);

        when(jwtTokenUtil.getRoles(anyString())).thenReturn(roles);

        boolean result = jwtTokenValidator.isFitsRoleUser(validationJwtToken);
        assertTrue(result);
    }

    static List<Arguments> providerCorrectRoleList() {
        return List.of(
                Arguments.of(List.of(ROLE_USER.name())),
                Arguments.of(List.of(ROLE_ADMIN.name())));
    }

}