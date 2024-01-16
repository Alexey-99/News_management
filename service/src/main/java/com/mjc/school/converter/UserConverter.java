package com.mjc.school.converter;

import com.mjc.school.model.user.User;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import com.mjc.school.validation.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.mjc.school.model.user.User.UserRole.ROLE_USER;

@RequiredArgsConstructor
@Component
public class UserConverter {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public CustomUserDetails toUserDetails(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .grantedAuthorities(Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getRole().name())))
                .build();
    }

    public User fromRegistrationUserDTO(RegistrationUserDto registrationUserDto) {
        return User.builder()
                .login(registrationUserDto.getLogin())
                .password(registrationUserDto.getPassword())
                .role(roleRepository.getByName(ROLE_USER.name()))
                .build();
    }

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(roleConverter.toDTO(user.getRole()))
                .build();
    }
}