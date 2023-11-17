package com.mjc.school.service.user.impl;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

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
                User user = userConverter.fromRegistrationUserDTO(userDto);
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepository.save(user);
                return true;
            } else {
                log.log(WARN, "Exists user with login: " + userDto.getLogin());
                throw new ServiceBadRequestParameterException("service.exception.registration.login.not_valid.exists");
            }
        } else {
            log.log(ERROR, "The entered passwords do not match.");
            throw new ServiceBadRequestParameterException("service.exception.registration.passwords_not_match");
        }
    }

    @Override
    @Transactional
    public boolean changeRole(UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException {
        String passwordBD = userRepository.getPasswordByLogin(userChangeRoleDto.getAdminLogin());
        String passwordEnteredEncoded = passwordEncoder.encode(userChangeRoleDto.getAdminPassword());
        if (passwordBD.equals(passwordEnteredEncoded)) {
            if (userRepository.existsByLogin(userChangeRoleDto.getUserLogin())) {
                userRepository.changeRole(userChangeRoleDto.getUserLogin(), userChangeRoleDto.getRoleId());
                return true;
            } else {
                log.log(WARN, "Not found user with login " + userChangeRoleDto.getUserLogin());
                throw new ServiceBadRequestParameterException("service.exception.change_role.user_login.not_valid.not_exists");
            }
        } else {
            log.log(ERROR, "The administrator login or password was entered incorrectly.");
            throw new ServiceBadRequestParameterException("service.exception.change_role.incorrect_admin_password_or_login");
        }
    }
}