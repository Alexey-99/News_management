package com.mjc.school.service.user;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.validation.dto.RegistrationUserDto;

public interface UserService {
    boolean create(RegistrationUserDto userDto) throws ServiceBadRequestParameterException;
}