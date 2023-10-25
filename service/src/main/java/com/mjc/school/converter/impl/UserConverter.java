package com.mjc.school.converter.impl;

import com.mjc.school.model.User;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserConverter {
    public CustomUserDetails toCustomUserDetails(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .grantedAuthorities(Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getRole().name())))
                .build();
    }
}