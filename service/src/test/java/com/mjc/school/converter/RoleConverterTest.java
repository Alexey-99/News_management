package com.mjc.school.converter;

import com.mjc.school.model.user.Role;
import com.mjc.school.validation.dto.RoleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RoleConverterTest {
    @InjectMocks
    private RoleConverter roleConverter;

    @Test
    void toUserDTO() {
        Role role = new Role(1, ROLE_USER);

        RoleDTO roleDTOExpected = new RoleDTO(role.getId(), role.getRole().name());

        RoleDTO roleDTOActual = roleConverter.toDTO(role);
        assertEquals(roleDTOExpected, roleDTOActual);
    }
}