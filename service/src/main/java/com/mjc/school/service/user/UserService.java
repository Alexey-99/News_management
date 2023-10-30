package com.mjc.school.service.user;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;

public interface UserService {
    boolean create(RegistrationUserDto userDto) throws ServiceBadRequestParameterException;

    boolean changeRole(UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException;
}