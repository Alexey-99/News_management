package com.mjc.school.converter.impl;

import com.mjc.school.converter.RoleConverter;
import com.mjc.school.model.user.Role;
import com.mjc.school.validation.dto.RoleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RoleConverterImpl implements RoleConverter {
    @Override
    public RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getRole().name())
                .build();
    }
}
