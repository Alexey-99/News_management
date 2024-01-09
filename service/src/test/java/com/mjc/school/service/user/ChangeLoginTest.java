package com.mjc.school.service.user;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.user.UserChangeLoginDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeLoginTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void changeLogin_when_userNotExistsById() {
        UserChangeLoginDto userChangeLoginDto =
                new UserChangeLoginDto(1, "newLogin");

        when(userRepository.existsById(anyLong())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> userService.changeLogin(userChangeLoginDto));
        assertEquals("service.exception.change_login.user_id.not_valid.not_exists",
                exceptionActual.getMessage());
    }

    @Test
    void changeLogin_when_userExistsById_and_foundUserByNewLogin() {
        UserChangeLoginDto userChangeLoginDto =
                new UserChangeLoginDto(1, "newLogin");

        when(userRepository.existsById(anyLong())).thenReturn(true);

        when(userRepository.notExistsByLogin(anyString())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> userService.changeLogin(userChangeLoginDto));
        assertEquals("service.exception.change_login.user_new_login.not_valid.exists",
                exceptionActual.getMessage());
    }

    @Test
    void changeLogin_when_userExistsById_and_notFoundUserByNewLogin() throws ServiceBadRequestParameterException {
        UserChangeLoginDto userChangeLoginDto =
                new UserChangeLoginDto(1, "newLogin");

        when(userRepository.existsById(anyLong())).thenReturn(true);

        when(userRepository.notExistsByLogin(anyString())).thenReturn(true);

        boolean resultActual = userService.changeLogin(userChangeLoginDto);
        assertTrue(resultActual);
    }
}