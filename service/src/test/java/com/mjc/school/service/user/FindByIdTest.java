package com.mjc.school.service.user;

import com.mjc.school.converter.impl.UserConverterImpl;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByIdTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverterImpl userConverter;

    @Test
    void findById_when_foundUserById() throws ServiceNoContentException {
        long userId = 1;

        User userFromDB = User.builder().id(userId).build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userFromDB));

        UserDTO userDTOExpected = UserDTO.builder().id(userFromDB.getId()).build();
        when(userConverter.toUserDTO(any(User.class))).thenReturn(userDTOExpected);

        UserDTO userDTOActual = userService.findById(userId);
        assertEquals(userDTOExpected, userDTOActual);
    }

    @Test
    void findById_when_notFoundUserById() {
        long userId = 1;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceNoContentException.class,
                () -> userService.findById(userId));
    }
}