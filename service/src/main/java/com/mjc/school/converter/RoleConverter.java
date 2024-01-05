package com.mjc.school.converter;

import com.mjc.school.model.user.Role;
import com.mjc.school.validation.dto.RoleDTO;

public interface RoleConverter{
    RoleDTO toDTO(Role role);
}
