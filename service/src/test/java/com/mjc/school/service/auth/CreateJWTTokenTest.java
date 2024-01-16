package com.mjc.school.service.auth;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.impl.AuthServiceImpl;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        String tokenActual = authService.createAuthToken(createJwtTokenRequest);
        assertEquals(jwtTokenExpected, tokenActual);
    }

//    @Test
//    void create_when_passwordsNotEqual() {
//        RegistrationUserDto registrationUserDtoTesting = RegistrationUserDto.builder()
//                .password("user_password")
//                .password("user_password_other")
//                .build();
//
//        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
//                () -> userService.create(registrationUserDtoTesting));
//        assertEquals("service.exception.registration.passwords_not_match", exceptionActual.getMessage());
//    }
//
//    @Test
//    void create_when_passwordsEqual_and_existsUserByLogin() {
//        RegistrationUserDto registrationUserDtoTesting = RegistrationUserDto.builder()
//                .password("user_password")
//                .confirmPassword("user_password")
//                .build();
//
//        when(userRepository.notExistsByLogin(registrationUserDtoTesting.getLogin()))
//                .thenReturn(false);
//
//        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
//                () -> userService.create(registrationUserDtoTesting));
//        assertEquals("service.exception.registration.login.not_valid.exists", exceptionActual.getMessage());
//    }
//
//    @Test
//    void create_when_passwordsEqual_and_notExistsUserByLogin() throws ServiceBadRequestParameterException {
//        RegistrationUserDto registrationUserDtoTesting = RegistrationUserDto.builder()
//                .password("user_password")
//                .confirmPassword("user_password")
//                .build();
//
//        when(userRepository.notExistsByLogin(registrationUserDtoTesting.getLogin()))
//                .thenReturn(true);
//
//        User userConvert = User.builder().password(registrationUserDtoTesting.getPassword()).build();
//        when(userConverter.fromRegistrationUserDTO(registrationUserDtoTesting))
//                .thenReturn(userConvert);
//
//        String passwordEncoded = "password";
//        when(passwordEncoder.encode(anyString())).thenReturn(passwordEncoded);
//
//        userConvert.setPassword(passwordEncoded);
//
//        when(userRepository.save(userConvert)).thenReturn(userConvert);
//
//        boolean actualResult = userService.create(registrationUserDtoTesting);
//        assertTrue(actualResult);
//    }
}