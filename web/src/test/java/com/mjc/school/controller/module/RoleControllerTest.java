package com.mjc.school.controller.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.AuthorController;
import com.mjc.school.controller.RoleController;
import com.mjc.school.service.author.AuthorService;
import com.mjc.school.service.role.RoleService;
import com.mjc.school.validation.dto.AuthorDTO;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.RoleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {
    @InjectMocks
    private RoleController roleController;
    @Mock
    private RoleService roleService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            findAll(): Return status 200 and List of roles.
               """)
    void findAll() throws Exception {
        List<RoleDTO> roleDTOListExpected = List.of(
                new RoleDTO(1, "ROLE_USER"),
                new RoleDTO(2, "ROLE_ADMIN"));

        when(roleService.findAll())
                .thenReturn(roleDTOListExpected);

        mockMvc.perform(get("/api/v2/role/all"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String actualContentType = result.getResponse().getContentType();
                    assertEquals(APPLICATION_JSON_VALUE, actualContentType);
                })
                .andExpect(result -> {
                    String actualContentJson = result.getResponse().getContentAsString();
                    String expectedContentJson = objectMapper.writeValueAsString(roleDTOListExpected);
                    assertNotNull(actualContentJson);
                    assertEquals(expectedContentJson, actualContentJson);
                });
    }
}
