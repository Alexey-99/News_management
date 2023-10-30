package com.mjc.school.converter.impl;

import com.mjc.school.model.User;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.validation.dto.RegistrationUserDto;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;

import static com.mjc.school.model.User.UserRole.ROLE_USER;

@RequiredArgsConstructor
@Component
public class UserConverter implements Function<User, CustomUserDetails>/*, Converter<UserDto, User>*/ {
    private final RoleRepository roleRepository;

    @Override
    public CustomUserDetails apply(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .grantedAuthorities(Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getRole().name())))
                .build();
    }

    //@Override
    public User fromDTO(RegistrationUserDto entityDTO) {
        return User.builder()
                .login(entityDTO.getLogin())
                .password(entityDTO.getPassword())
                .email(entityDTO.getEmail())
                .role(roleRepository.getByName(ROLE_USER.name()))
                .build();
    }

    //@Override
//    public UserDto toDTO(User entity) {
//        return UserDto.builder()
//                .login(entity.getLogin())
//                .password(entity.getPassword())
//                .email(entity.getEmail())
//                .build();
//    }
}