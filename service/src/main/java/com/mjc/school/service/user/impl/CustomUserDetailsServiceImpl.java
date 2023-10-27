package com.mjc.school.service.user.impl;

import com.mjc.school.converter.impl.UserConverter;
import com.mjc.school.model.User;
import com.mjc.school.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login).orElseThrow(() -> {
            log.log(WARN, "Not found user with login " + login);
            return new UsernameNotFoundException("Not found user with login " + login);
        });
        return userConverter.apply(user);
    }
}