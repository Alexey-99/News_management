package com.mjc.school.service.role;

import com.mjc.school.converter.RoleConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.user.Role;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.service.role.impl.RoleServiceImpl;
import com.mjc.school.validation.dto.RoleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.mjc.school.model.user.User.UserRole.ROLE_ADMIN;
import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllTest {
    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleConverter roleConverter;

    @Test
    void findAll_when_foundRole_and_sortIdAsc() throws ServiceNoContentException {
        List<Role> roleList = List.of(
                Role.builder().id(1).role(ROLE_USER).build(),
                Role.builder().id(2).role(ROLE_ADMIN).build());


        when(roleRepository.findAllSortIdAsc())
                .thenReturn(roleList);

        when(roleConverter.toDTO(Role.builder().id(1).role(ROLE_USER).build()))
                .thenReturn(RoleDTO.builder().id(1).name(ROLE_USER.name()).build());
        when(roleConverter.toDTO(Role.builder().id(2).role(ROLE_ADMIN).build()))
                .thenReturn(RoleDTO.builder().id(2).name(ROLE_ADMIN.name()).build());

        List<RoleDTO> roleDTOList = List.of(
                RoleDTO.builder().id(1).name(ROLE_USER.name()).build(),
                RoleDTO.builder().id(2).name(ROLE_ADMIN.name()).build());

        List<RoleDTO> userDTOListActual = roleService.findAll();
        assertEquals(roleDTOList, userDTOListActual);
    }

    @Test
    void findAll_when_notFoundRoles() {
        when(roleRepository.findAllSortIdAsc()).thenReturn(List.of());

        assertThrows(ServiceNoContentException.class, () -> roleService.findAll());
    }
}