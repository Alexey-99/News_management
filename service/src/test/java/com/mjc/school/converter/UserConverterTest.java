package com.mjc.school.converter;

import com.mjc.school.model.user.Role;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
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

    @Test
    void toUserDetails() {
        User user = new User(1, "login_test", "password_test", "email_test@gmail.com",
                new Role(1, ROLE_USER));
        CustomUserDetails customUserDetailsExpected = CustomUserDetails.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .grantedAuthorities(Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getRole().name())))
                .build();

        CustomUserDetails customUserDetailsActual = userConverter.toUserDetails(user);
        assertEquals(customUserDetailsExpected, customUserDetailsActual);

    }

    @Test
    void fromRegistrationUserDTO() {
        RegistrationUserDto registrationUserDtoTesting = new RegistrationUserDto("login_test", "password_test",
                "password_test", "email_test@gmail.com");
        User user = User.builder()
                .login("login_test")
                .password("password_test")
                .email("email_test@gmail.com")
                .role(Role.builder()
                        .id(1)
                        .role(ROLE_USER)
                        .build())
                .build();

        when(roleRepository.getByName(ROLE_USER.name()))
                .thenReturn(Role.builder().id(1).role(ROLE_USER).build());

        User userActual = userConverter.fromRegistrationUserDTO(registrationUserDtoTesting);
        assertEquals(user, userActual);
    }
}