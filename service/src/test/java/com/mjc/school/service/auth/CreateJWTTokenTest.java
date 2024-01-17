package com.mjc.school.service.auth;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.impl.AuthServiceImpl;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateJWTTokenTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private CustomUserDetailsServiceImpl userDetailsService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserConverter userConverter;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void createAuthToken_when_authenticationManagerThrowException() {
        CreateJwtTokenRequest createJwtTokenRequest =
                new CreateJwtTokenRequest("user", "123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> authService.createAuthToken(createJwtTokenRequest));
        assertEquals("service.exception.create_auth_token.incorrect_password_or_login", exceptionActual.getMessage());
    }

    @Test
    void createAuthToken() throws ServiceBadRequestParameterException {
        CreateJwtTokenRequest createJwtTokenRequest =
                new CreateJwtTokenRequest("user", "123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        CustomUserDetails customUserDetails = new CustomUserDetails();
        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(customUserDetails);

        String jwtTokenExpected = "token";
        when(jwtTokenUtil.generateAccessToken(any(CustomUserDetails.class)))
                .thenReturn(jwtTokenExpected);
        Date expiredDate = new Date();
        when(jwtTokenUtil.getExpirationAccessToken(anyString()))
                .thenReturn(expiredDate);

        List<String> roles = List.of("ROLE_ADMIN");
        when(jwtTokenUtil.getRoles(anyString()))
                .thenReturn(roles);

        JwtTokenResponse jwtTokenResponseExpected = JwtTokenResponse.builder()
                .accessToken(jwtTokenExpected)
                .expiredDate(expiredDate)
                .login(createJwtTokenRequest.getUserName())
                .userRole(roles.get(0))
                .build();

        JwtTokenResponse jwtTokenResponseActual = authService.createAuthToken(createJwtTokenRequest);
        assertEquals(jwtTokenResponseExpected, jwtTokenResponseActual);
    }
}