package com.mjc.school.converter;

import com.mjc.school.model.user.Role;
import com.mjc.school.validation.dto.RoleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RoleConverter {
    public RoleDTO toDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getRole().name())
                .build();
    }
}