package com.mjc.school.service.user;

import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.user.impl.UserServiceImpl;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPaginationTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PaginationService paginationService;

    @Test
    void getPagination() {
        List<UserDTO> userList = List.of(
                UserDTO.builder().id(25).login("A_user").build(),
                UserDTO.builder().id(14).login("B_user").build(),
                UserDTO.builder().id(30).login("C_user").build(),
                UserDTO.builder().id(30).login("D_user").build(),
                UserDTO.builder().id(30).login("Z_user").build());

        long countAllElements = 10;
        int page = 1;
        int size = 5;
        int maxNumberPageExpected = 2;

        when(paginationService.calcMaxNumberPage(anyLong(), anyInt()))
                .thenReturn(maxNumberPageExpected);

        Pagination<UserDTO> userDTOPaginationExpected = Pagination.<UserDTO>builder()
                .entity(userList)
                .size(size)
                .numberPage(page)
                .countAllEntity(countAllElements)
                .maxNumberPage(maxNumberPageExpected)
                .build();
        Pagination<UserDTO> userDTOPaginationActual =
                userService.getPagination(userList, countAllElements, page, size);
        assertEquals(userDTOPaginationExpected, userDTOPaginationActual);
    }
}