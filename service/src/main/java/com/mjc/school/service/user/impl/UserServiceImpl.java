package com.mjc.school.service.user.impl;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public boolean create(RegistrationUserDto userDto) throws ServiceBadRequestParameterException {
        if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
            if (userRepository.notExistsByLogin(userDto.getLogin())) {
                User user = userConverter.fromDTO(userDto);
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepository.save(user);
                return true;
            } else {
                throw new ServiceBadRequestParameterException("Логин уже существует");
            }
        } else {
            throw new ServiceBadRequestParameterException("Пароли не совпадают");
        }

    }
}