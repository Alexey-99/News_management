package com.mjc.school.converter;

import com.mjc.school.model.user.User;
import com.mjc.school.validation.dto.security.CustomUserDetails;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserDTO;

public interface UserConverter {
    CustomUserDetails toUserDetails(User user);

    User fromRegistrationUserDTO(RegistrationUserDto registrationUserDto);

    UserDTO toUserDTO(User user);
}
