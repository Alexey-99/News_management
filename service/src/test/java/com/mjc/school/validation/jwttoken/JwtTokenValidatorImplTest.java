package com.mjc.school.validation.jwttoken;

import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class JwtTokenValidatorImplTest {
    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void isFitsRoleUser_when_validationJwtTokenIsNull() {
        ValidationJwtToken validationJwtToken = null;

        CustomAuthenticationException exception = assertThrows(CustomAuthenticationException.class,
                () -> jwtTokenUtil.isUser(validationJwtToken));
        assertEquals("exception.access_without_authorization", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource(value = "providerValidationJwtToken_when_throwCustomAuthenticationException")
    void isFitsRoleUser_when_throwCustomAuthenticationException(ValidationJwtToken validationJwtToken) {
        CustomAuthenticationException exception = assertThrows(CustomAuthenticationException.class,
                () -> jwtTokenUtil.isUser(validationJwtToken));
        assertEquals("exception.access_without_authorization", exception.getMessage());
    }

    static List<Arguments> providerValidationJwtToken_when_throwCustomAuthenticationException() {
        return List.of(
                Arguments.of(new ValidationJwtToken(null)),
                Arguments.of(new ValidationJwtToken("token")));
    }
}