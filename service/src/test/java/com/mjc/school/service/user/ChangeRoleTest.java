package com.mjc.school.service.user;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeRoleTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void changeRole_when_passwordsEqual_and_notExistsUserByLogin() {
        UserChangeRoleDto userChangeRoleDto = UserChangeRoleDto.builder()
                .userLogin("userLogin")
                .roleId(2)
                .build();

        when(userRepository.existsByLogin(anyString())).thenReturn(false);

        ServiceBadRequestParameterException exceptionActual =
                assertThrows(ServiceBadRequestParameterException.class,
                        () -> userService.changeRole(userChangeRoleDto));
        assertEquals("service.exception.change_role.user_login.not_valid.not_exists",
                exceptionActual.getMessage());
    }

    @Test
    void changeRole_when_passwordsEqual_and_existsUserByLogin() throws ServiceBadRequestParameterException {
        UserChangeRoleDto userChangeRoleDto = UserChangeRoleDto.builder()
                .userLogin("userLogin")
                .roleId(2)
                .build();

        when(userRepository.existsByLogin(anyString())).thenReturn(true);

        boolean actualResult = userService.changeRole(userChangeRoleDto);
        assertTrue(actualResult);
    }
}