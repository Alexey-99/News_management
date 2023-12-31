package com.mjc.school.service;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
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

        when(userRepository.save(userConvert)).thenReturn(userConvert);

        boolean actualResult = userService.create(registrationUserDtoTesting);
        assertTrue(actualResult);
    }

    @Test
    void changeRole_when_passwordsNotEqual() {
        UserChangeRoleDto userChangeRoleDto = UserChangeRoleDto.builder()
                .adminLogin("adminLogin")
                .adminPassword("adminPassword")
                .build();

        String passwordFromDBEncoded = "adminPassword";
        when(userRepository.getPasswordByLogin(userChangeRoleDto.getAdminLogin()))
                .thenReturn(passwordFromDBEncoded);

        String passwordEnteredEncoded = "adminPasswordOther";
        when(passwordEncoder.encode(userChangeRoleDto.getAdminPassword()))
                .thenReturn(passwordEnteredEncoded);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> userService.changeRole(userChangeRoleDto));
        assertEquals("service.exception.change_role.incorrect_admin_password_or_login",
                exceptionActual.getMessage());
    }

    @Test
    void changeRole_when_passwordsEqual_and_notExistsUserByLogin() {
        UserChangeRoleDto userChangeRoleDto = UserChangeRoleDto.builder()
                .adminLogin("adminLogin")
                .adminPassword("adminPassword")
                .build();

        String passwordFromDBEncoded = "adminPassword";
        when(userRepository.getPasswordByLogin(userChangeRoleDto.getAdminLogin()))
                .thenReturn(passwordFromDBEncoded);

        String passwordEnteredEncoded = "adminPassword";
        when(passwordEncoder.encode(userChangeRoleDto.getAdminPassword()))
                .thenReturn(passwordEnteredEncoded);

        when(userRepository.existsByLogin(userChangeRoleDto.getUserLogin())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> userService.changeRole(userChangeRoleDto));
        assertEquals("service.exception.change_role.user_login.not_valid.not_exists",
                exceptionActual.getMessage());
    }

    @Test
    void changeRole_when_passwordsEqual_and_existsUserByLogin() throws ServiceBadRequestParameterException {
        UserChangeRoleDto userChangeRoleDto = UserChangeRoleDto.builder()
                .adminLogin("adminLogin")
                .adminPassword("adminPassword")
                .build();

        String passwordFromDBEncoded = "adminPassword";
        when(userRepository.getPasswordByLogin(userChangeRoleDto.getAdminLogin()))
                .thenReturn(passwordFromDBEncoded);

        String passwordEnteredEncoded = "adminPassword";
        when(passwordEncoder.encode(userChangeRoleDto.getAdminPassword()))
                .thenReturn(passwordEnteredEncoded);

        when(userRepository.existsByLogin(userChangeRoleDto.getUserLogin())).thenReturn(true);

        boolean actualResult = userService.changeRole(userChangeRoleDto);
        assertTrue(actualResult);
    }
}