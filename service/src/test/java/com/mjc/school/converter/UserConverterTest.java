package com.mjc.school.converter;

import com.mjc.school.model.user.Role;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserConverterTest {
    @InjectMocks
    private UserConverter userConverter;
    @Mock
    private RoleRepository roleRepository;
    private static User user;
    private static User userActual;
    private static CustomUserDetails customUserDetailsExpected;
    private static CustomUserDetails customUserDetailsActual;
    private static RegistrationUserDto registrationUserDtoTesting;

    @Test
    void toUserDetails() {
        user = User.builder()
                .id(1)
                .login("login_test")
                .password("password_test")
                .email("email_test@gmail.com")
                .role(Role.builder()
                        .id(1)
                        .role(ROLE_USER)
                        .build())
                .build();

        customUserDetailsExpected = CustomUserDetails.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .grantedAuthorities(Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getRole().name())))
                .build();

        customUserDetailsActual = userConverter.toUserDetails(user);
        assertEquals(customUserDetailsExpected, customUserDetailsActual);

    }

    @Test
    void fromRegistrationUserDTO() {
        registrationUserDtoTesting = RegistrationUserDto.builder()
                .login("login_test")
                .password("password_test")
                .confirmPassword("password_test")
                .email("email_test@gmail.com")
                .build();

        user = User.builder()
                .login("login_test")
                .password("password_test")
                .email("email_test@gmail.com")
                .role(Role.builder()
                        .id(1)
                        .role(ROLE_USER)
                        .build())
                .build();

        when(roleRepository.getByName(ROLE_USER.name()))
                .thenReturn(Role.builder()
                        .id(1)
                        .role(ROLE_USER)
                        .build());

        userActual = userConverter.fromRegistrationUserDTO(registrationUserDtoTesting);
        assertEquals(user, userActual);
    }

    @AfterAll
    static void afterAll() {
        customUserDetailsActual = null;
        customUserDetailsExpected = null;
        user = null;
        userActual = null;
        registrationUserDtoTesting = null;
    }
}