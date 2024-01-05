package com.mjc.school.service;

import com.mjc.school.converter.impl.UserConverterImpl;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceImplTest {
    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverterImpl userConverter;

    @Test
    void loadUserByUsername_when_notFoundUserByLogin() {
        String userLogin = "userLogin";

        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());

        UsernameNotFoundException exceptionActual = assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(userLogin));
        assertEquals("service.exception.user_not_exists", exceptionActual.getMessage());
    }

    @Test
    void loadUserByUsername_when_foundUserByLogin() {
        String userLogin = "userLogin";

        User userFromDB = User.builder().id(1).login(userLogin).build();
        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.of(userFromDB));

        CustomUserDetails userDetailsExpected = CustomUserDetails.builder().id(1).login(userLogin).build();
        when(userConverter.toUserDetails(userFromDB)).thenReturn(userDetailsExpected);

        UserDetails customUserDetailsActual = customUserDetailsService.loadUserByUsername(userLogin);
        assertEquals(userDetailsExpected, customUserDetailsActual);
    }
}