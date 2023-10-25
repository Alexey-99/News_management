package com.mjc.school.service.user.impl;

import com.mjc.school.User;
import com.mjc.school.UserRepository;
import com.mjc.school.converter.impl.UserConverter;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(() ->
                new ServiceException("Not found user with username %s".formatted(username))
        );
        return userConverter.toCustomUserDetails(user);
    }
}