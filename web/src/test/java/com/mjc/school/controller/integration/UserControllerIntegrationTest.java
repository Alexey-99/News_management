package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeLoginDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class UserControllerIntegrationTest {
    private static final String AUTHORIZATION_HEADER_VALUE_START_WITH = "Bearer ";
    @Autowired
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    private String adminJwtToken;
    private String userJwtToken;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws ServiceBadRequestParameterException {
        if (adminJwtToken == null) {
            CreateJwtTokenRequest createJwtTokenRequestAdmin =
                    new CreateJwtTokenRequest("user", "123456");
            CreateJwtTokenRequest createJwtTokenRequestUser =
                    new CreateJwtTokenRequest("user_2", "123456");
            adminJwtToken = authService.createAuthToken(createJwtTokenRequestAdmin);
            userJwtToken = authService.createAuthToken(createJwtTokenRequestUser);
        }
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser() throws Exception {
        RegistrationUserDto registrationUserDto = RegistrationUserDto.builder()
                .login("login")
                .password("987654")
                .confirmPassword("987654")
                .build();
        String registrationUserDtoJson = objectMapper.writeValueAsString(registrationUserDto);
        mockMvc.perform(post("/api/v2/user/registration")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(registrationUserDtoJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidRegistrationUser")
    void createUser_and_notValidUser(RegistrationUserDto registrationUserDto) throws Exception {
        String registrationUserDtoJson = objectMapper.writeValueAsString(registrationUserDto);
        mockMvc.perform(post("/api/v2/user/registration")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(registrationUserDtoJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    static List<Arguments> providerNotValidRegistrationUser() {
        return List.of(
                Arguments.of(RegistrationUserDto.builder()
                        .login(null)
                        .password("987654")
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("   ")
                        .password("987654")
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("qw")
                        .password("987654")
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("user")
                        .password("987654")
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("login")
                        .password(null)
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("login")
                        .password("   ")
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("login")
                        .password("qw")
                        .confirmPassword("987654")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("login")
                        .password("987654")
                        .confirmPassword(null)
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("login")
                        .password("987654")
                        .confirmPassword("   ")
                        .build()),
                Arguments.of(RegistrationUserDto.builder()
                        .login("login")
                        .password("987654")
                        .confirmPassword("qw")
                        .build())
        );
    }

    @Test
    void changeRole_roleAdmin() throws Exception {
        UserChangeRoleDto userChangeRoleDto = new UserChangeRoleDto("user_2", 2);
        String userChangeRoleDtoJson = objectMapper.writeValueAsString(userChangeRoleDto);
        mockMvc.perform(patch("/api/v2/user/role")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeRoleDtoJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void changeRole_roleUser() throws Exception {
        UserChangeRoleDto userChangeRoleDto = new UserChangeRoleDto("user_2", 2);
        String userChangeRoleDtoJson = objectMapper.writeValueAsString(userChangeRoleDto);
        mockMvc.perform(patch("/api/v2/user/role")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeRoleDtoJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void changeRole_roleGuest() throws Exception {
        UserChangeRoleDto userChangeRoleDto = new UserChangeRoleDto("user_2", 2);
        String userChangeRoleDtoJson = objectMapper.writeValueAsString(userChangeRoleDto);
        mockMvc.perform(patch("/api/v2/user/role")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeRoleDtoJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNotValidUserChangeRoleDto")
    void changeRole_roleAdmin_and_notValidUser(UserChangeRoleDto userChangeRoleDto) throws Exception {
        String userChangeRoleDtoJson = objectMapper.writeValueAsString(userChangeRoleDto);
        mockMvc.perform(patch("/api/v2/user/role")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeRoleDtoJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    static List<Arguments> providerNotValidUserChangeRoleDto() {
        return List.of(
                Arguments.of(new UserChangeRoleDto(null, 2)),
                Arguments.of(new UserChangeRoleDto("   ", 2)),
                Arguments.of(new UserChangeRoleDto("qw", 2)),
                Arguments.of(new UserChangeRoleDto("login", 2)),
                Arguments.of(new UserChangeRoleDto("user_2", 3))
        );
    }


    @Test
    void changeLogin_roleAdmin() throws Exception {
        UserChangeLoginDto userChangeLoginDto = new UserChangeLoginDto(2, "Alex");
        String userChangeLoginDtoJson = objectMapper.writeValueAsString(userChangeLoginDto);
        mockMvc.perform(patch("/api/v2/user/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeLoginDtoJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void changeLogin_roleUser() throws Exception {
        UserChangeLoginDto userChangeLoginDto = new UserChangeLoginDto(2, "Alex");
        String userChangeLoginDtoJson = objectMapper.writeValueAsString(userChangeLoginDto);
        mockMvc.perform(patch("/api/v2/user/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeLoginDtoJson)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void changeLogin_roleGuest() throws Exception {
        UserChangeLoginDto userChangeLoginDto = new UserChangeLoginDto(2, "Alex");
        String userChangeLoginDtoJson = objectMapper.writeValueAsString(userChangeLoginDto);
        mockMvc.perform(patch("/api/v2/user/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(userChangeLoginDtoJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findByLogin_roleAdmin_and_foundObject() throws Exception {
        String login = "user";
        mockMvc.perform(get("/api/v2/user/login/{login}", login)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByLogin_roleAdmin_and_notFoundObject() throws Exception {
        String login = "Alex";
        mockMvc.perform(get("/api/v2/user/login/{login}", login)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByLogin_roleUser() throws Exception {
        String login = "Alex";
        mockMvc.perform(get("/api/v2/user/login/{login}", login)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void findByLogin_roleGuest() throws Exception {
        String login = "Alex";
        mockMvc.perform(get("/api/v2/user/login/{login}", login))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void findById_roleAdmin_and_foundObject() throws Exception {
        String userId = "1";
        mockMvc.perform(get("/api/v2/user/{id}", userId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findById_roleAdmin_and_notFoundObject() throws Exception {
        String userId = "3";
        mockMvc.perform(get("/api/v2/user/{id}", userId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findById_roleUser() throws Exception {
        String userId = "1";
        mockMvc.perform(get("/api/v2/user/{id}", userId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void findById_roleGuest() throws Exception {
        String userId = "1";
        mockMvc.perform(get("/api/v2/user/{id}", userId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void deleteById_roleAdmin_and_foundObject() throws Exception {
        String userId = "2";
        mockMvc.perform(delete("/api/v2/user/{id}", userId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_roleAdmin_and_notFoundObject() throws Exception {
        String userId = "3";
        mockMvc.perform(delete("/api/v2/user/{id}", userId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_roleUser() throws Exception {
        String userId = "2";
        mockMvc.perform(delete("/api/v2/user/{id}", userId)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById_roleGuest() throws Exception {
        String userId = "2";
        mockMvc.perform(delete("/api/v2/user/{id}", userId))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void findAll_roleAdmin_and_foundObject_and_foundObjectsByCurrentPage() throws Exception {
        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/user/all")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findAll_roleAdmin_and_foundObject_and_notFoundObjectsByCurrentPage() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        mockMvc.perform(get("/api/v2/user/all")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findAll_roleUser() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";
        mockMvc.perform(get("/api/v2/user/all")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void findAll_roleGuest() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";
        mockMvc.perform(get("/api/v2/user/all")
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findByRole_roleAdmin_and_foundObject_and_foundObjectsByCurrentPage() throws Exception {
        String page = "1";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        String role = "user";

        mockMvc.perform(get("/api/v2/user/role/{role}", role)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    void findByRole_roleAdmin_and_foundObject_and_notFoundObjectsByCurrentPage() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        String role = "user";

        mockMvc.perform(get("/api/v2/user/role/{role}", role)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource(value = "providerNumberPages")
    void findByRole_roleAdmin_and_notFoundObject_and_notFoundObjectsByCurrentPage(String page) throws Exception {
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        String role = "alex";

        mockMvc.perform(get("/api/v2/user/role/{role}", role)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + adminJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isNoContent());
    }

    static List<Arguments> providerNumberPages() {
        return List.of(
                Arguments.of("1"),
                Arguments.of("2"),
                Arguments.of("3"));
    }

    @Test
    void findByRole_roleUser() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        String role = "user";

        mockMvc.perform(get("/api/v2/user/role/{role}", role)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType)
                        .header(AUTHORIZATION, AUTHORIZATION_HEADER_VALUE_START_WITH + userJwtToken))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isForbidden());
    }

    @Test
    void findByRole_roleGuest() throws Exception {
        String page = "2";
        String size = "5";
        String sortType = "ASC";
        String sortField = "name";

        String role = "user";

        mockMvc.perform(get("/api/v2/user/role/{role}", role)
                        .param("size", size)
                        .param("page", page)
                        .param("sort-field", sortField)
                        .param("sort-type", sortType))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }
}