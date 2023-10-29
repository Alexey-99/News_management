package com.mjc.school.service.user.impl;

import com.mjc.school.converter.impl.UserConverter;
import com.mjc.school.model.User;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public boolean create(RegistrationUserDto userDto) {
        User user = userConverter.fromDTO(userDto);
        userRepository.save(user);
        return true;
    }
}