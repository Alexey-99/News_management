package com.mjc.school.service.user;

import com.mjc.school.converter.impl.UserConverterImpl;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.user.Role;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.RoleDTO;
import com.mjc.school.validation.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.mjc.school.model.user.User.UserRole.ROLE_ADMIN;
import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindByRoleTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaginationService paginationService;
    @Mock
    private UserConverterImpl userConverter;

    @Test
    void findByRole_when_foundUsers_and_sortLoginAsc() throws ServiceNoContentException {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "login";

        String role = "user";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<User> userList = List.of(
                User.builder().id(25).login("A_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(14).login("B_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(30).login("C_user").role(new Role(1, ROLE_USER)).build());

        when(userRepository.findByRoleSortLoginAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(userList);

        when(userConverter.toUserDTO(User.builder().id(25).login("A_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(25).login("A_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(14).login("B_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(14).login("B_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(30).login("C_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(30).login("C_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());


        List<UserDTO> userDTOList = List.of(
                UserDTO.builder().id(25).login("A_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(14).login("B_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(30).login("C_user").role(new RoleDTO(1, ROLE_USER.name())).build());

        List<UserDTO> tagDTOListActual = userService.findByRole(role, page, size, sortField, sortType);
        assertEquals(userDTOList, tagDTOListActual);
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortLoginDesc")
    void findByRole_when_foundUsers_and_sortLoginDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String role = "user";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<User> userList = List.of(
                User.builder().id(30).login("C_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(14).login("B_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(25).login("A_user").role(new Role(1, ROLE_USER)).build());

        when(userRepository.findByRoleSortLoginDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(userList);

        when(userConverter.toUserDTO(User.builder().id(25).login("A_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(25).login("A_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(14).login("B_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(14).login("B_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(30).login("C_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(30).login("C_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());


        List<UserDTO> userDTOList = List.of(
                UserDTO.builder().id(30).login("C_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(14).login("B_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(25).login("A_user").role(new RoleDTO(1, ROLE_USER.name())).build());

        List<UserDTO> tagDTOListActual = userService.findByRole(role, page, size, sortField, sortType);
        assertEquals(userDTOList, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortLoginDesc() {
        return List.of(
                Arguments.of("login", "DESC"),
                Arguments.of("login", null),
                Arguments.of("login", "type"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdAsc")
    void findAllWithPages_when_foundUsers_and_sortIdAsc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String role = "user";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<User> userList = List.of(
                User.builder().id(14).login("B_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(25).login("A_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(30).login("C_user").role(new Role(1, ROLE_USER)).build());

        when(userRepository.findByRoleSortIdAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(userList);

        when(userConverter.toUserDTO(User.builder().id(25).login("A_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(25).login("A_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(14).login("B_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(14).login("B_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(30).login("C_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(30).login("C_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());


        List<UserDTO> userDTOList = List.of(
                UserDTO.builder().id(14).login("B_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(25).login("A_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(30).login("C_user").role(new RoleDTO(1, ROLE_USER.name())).build());

        List<UserDTO> tagDTOListActual = userService.findByRole(role, page, size, sortField, sortType);
        assertEquals(userDTOList, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdAsc() {
        return List.of(
                Arguments.of("id", "ASC"),
                Arguments.of(null, "ASC"),
                Arguments.of("field", "ASC"));
    }

    @ParameterizedTest
    @MethodSource(value = "providerSortFieldAndType_when_sortIdDesc")
    void findAllWithPages_when_foundUsers_and_sortIdDesc(String sortField, String sortType) throws ServiceNoContentException {
        int page = 1;
        int size = 5;

        String role = "user";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        List<User> userList = List.of(
                User.builder().id(30).login("C_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(25).login("A_user").role(new Role(1, ROLE_USER)).build(),
                User.builder().id(14).login("B_user").role(new Role(1, ROLE_USER)).build());

        when(userRepository.findByRoleSortIdDesc(anyString(), anyInt(), anyInt()))
                .thenReturn(userList);

        when(userConverter.toUserDTO(User.builder().id(25).login("A_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(25).login("A_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(14).login("B_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(14).login("B_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());
        when(userConverter.toUserDTO(User.builder().id(30).login("C_user")
                .role(new Role(1, ROLE_USER)).build()))
                .thenReturn(UserDTO.builder().id(30).login("C_user")
                        .role(new RoleDTO(1, ROLE_USER.name())).build());

        List<UserDTO> userDTOList = List.of(
                UserDTO.builder().id(30).login("C_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(25).login("A_user").role(new RoleDTO(1, ROLE_USER.name())).build(),
                UserDTO.builder().id(14).login("B_user").role(new RoleDTO(1, ROLE_USER.name())).build());

        List<UserDTO> tagDTOListActual = userService.findByRole(role, page, size, sortField, sortType);
        assertEquals(userDTOList, tagDTOListActual);
    }

    static List<Arguments> providerSortFieldAndType_when_sortIdDesc() {
        return List.of(
                Arguments.of("id", "DESC"),
                Arguments.of("id", null),
                Arguments.of(null, "DESC"),
                Arguments.of(null, null),
                Arguments.of("field", "type"));
    }

    @Test
    void findByRole_when_notFoundUsers() {
        int page = 1;
        int size = 5;
        String sortType = "ASC";
        String sortField = "id";

        String role = "user";

        when(paginationService.calcNumberFirstElement(page, size)).thenReturn(0);

        when(userRepository.findByRoleSortIdAsc(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of());

        assertThrows(ServiceNoContentException.class,
                () -> userService.findByRole(role, page, size, sortField, sortType));
    }

    @Test
    void countAllByRole() {
        long countAllUsers = 3L;
        String role = "user";

        when(userRepository.countAllUsersByRole(anyString())).thenReturn(countAllUsers);

        long countAllActual = userService.countAllUsersByRole(role);
        long countAllExpected = 3L;

        assertEquals(countAllExpected, countAllActual);
    }


}