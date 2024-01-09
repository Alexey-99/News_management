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
class DeleteByIdTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;


    @Test
    void deleteById_when_userExistsById() {
        long userId = 1;

        when(userRepository.existsById(anyLong())).thenReturn(true);

        boolean resultActual = userService.deleteById(userId);
        assertTrue(resultActual);
    }

    @Test
    void deleteById_when_userNotExistsById() {
        long userId = 1;

        when(userRepository.existsById(anyLong())).thenReturn(false);

        boolean resultActual = userService.deleteById(userId);
        assertTrue(resultActual);
    }
}