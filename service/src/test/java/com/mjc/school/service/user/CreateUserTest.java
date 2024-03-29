package com.mjc.school.service.user;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserConverter userConverter;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @Test
    void create_when_passwordsNotEqual() {
        RegistrationUserDto registrationUserDtoTesting = RegistrationUserDto.builder()
                .password("user_password")
                .password("user_password_other")
                .build();

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> userService.create(registrationUserDtoTesting));
        assertEquals("service.exception.registration.passwords_not_match", exceptionActual.getMessage());
    }

    @Test
    void create_when_passwordsEqual_and_existsUserByLogin() {
        RegistrationUserDto registrationUserDtoTesting = RegistrationUserDto.builder()
                .password("user_password")
                .confirmPassword("user_password")
                .build();

        when(userRepository.notExistsByLogin(registrationUserDtoTesting.getLogin()))
                .thenReturn(false);

        ServiceBadRequestParameterException exceptionActual = assertThrows(ServiceBadRequestParameterException.class,
                () -> userService.create(registrationUserDtoTesting));
        assertEquals("service.exception.registration.login.not_valid.exists", exceptionActual.getMessage());
    }

    @Test
    void create_when_passwordsEqual_and_notExistsUserByLogin() throws ServiceBadRequestParameterException {
        RegistrationUserDto registrationUserDtoTesting = RegistrationUserDto.builder()
                .password("user_password")
                .confirmPassword("user_password")
                .build();

        when(userRepository.notExistsByLogin(registrationUserDtoTesting.getLogin()))
                .thenReturn(true);

        User userConvert = User.builder().password(registrationUserDtoTesting.getPassword()).build();
        when(userConverter.fromRegistrationUserDTO(registrationUserDtoTesting))
                .thenReturn(userConvert);

        String passwordEncoded = "password";
        when(passwordEncoder.encode(anyString())).thenReturn(passwordEncoded);

        userConvert.setPassword(passwordEncoded);

        when(userRepository.save(userConvert)).thenReturn(userConvert);

        boolean actualResult = userService.create(registrationUserDtoTesting);
        assertTrue(actualResult);
    }
}