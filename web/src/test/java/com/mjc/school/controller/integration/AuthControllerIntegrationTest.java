package com.mjc.school.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.controller.AuthController;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class AuthControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 201 and JWT token.
            """)
    void createAuthToken() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName("user")
                .password("123456")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if not found user by login.
            """)
    void createAuthToken_when_notFoundUser() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName("user_3")
                .password("123456")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if found user by login and not correct entered login - null.
            """)
    void createAuthToken_when_foundUser_and_loginIsNull() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName(null)
                .password("123456")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if found user by login and not correct entered login - is blank.
            """)
    void createAuthToken_when_foundUser_and_loginIsBlank() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName(null)
                .password("   ")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if found user by login and not correct entered login - not correct size.
            """)
    void createAuthToken_when_foundUser_and_loginNotCorrectSize() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName("q")
                .password("123456")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if found user by login and correct entered login and not correct entered password - is null.
            """)
    void createAuthToken_when_foundUser_and_loginCorrect_and_passwordIsNull() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName("user")
                .password(null)
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if found user by login and correct entered login and not correct entered password - is blank.
            """)
    void createAuthToken_when_foundUser_and_loginCorrect_and_passwordIsBlank() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName("user")
                .password("   ")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            createAuthToken(): Return status 400 if found user by login and correct entered login and not correct entered password - is not valid size.
            """)
    void createAuthToken_when_foundUser_and_loginCorrect_and_passwordIsNotValidSize() throws Exception {
        CreateJwtTokenRequest createJwtTokenRequest = CreateJwtTokenRequest.builder()
                .userName("user")
                .password("12")
                .build();
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(createJwtTokenRequest);

        mockMvc.perform(post("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andExpect(status().isBadRequest());
    }
}