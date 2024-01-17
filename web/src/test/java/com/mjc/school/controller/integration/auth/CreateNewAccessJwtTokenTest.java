package com.mjc.school.controller.integration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.model.user.User;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenRequest;
import com.mjc.school.validation.dto.user.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.logging.log4j.Level.DEBUG;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
@SpringBootTest
@TestPropertySource("/test_application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/schema.sql"})
@Sql(value = {"/data_before_method.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(value = {"/data_after_method.sql"}, executionPhase = AFTER_TEST_METHOD)
class CreateNewAccessJwtTokenTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    private ObjectMapper objectMapper;
    private String adminJwtToken;
    private String userJwtToken;

    @BeforeEach
    void setUp() throws ServiceBadRequestParameterException {
        if (adminJwtToken == null) {
            CreateJwtTokenRequest createJwtTokenRequestAdmin = new CreateJwtTokenRequest("user", "123456");
            CreateJwtTokenRequest createJwtTokenRequestUser = new CreateJwtTokenRequest("user_2", "123456");
            adminJwtToken = authService.createAuthToken(createJwtTokenRequestAdmin).getAccessToken();
            userJwtToken = authService.createAuthToken(createJwtTokenRequestUser).getAccessToken();
        }
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName(value = """
            getNewAccessToken(): Return status 200 and JWT token.
            """)
    void getNewAccessToken() throws Exception {
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(adminJwtToken);
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(jwtTokenRequest);

        mockMvc.perform(put("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = """
            getNewAccessToken(): Return status 400.
            If not correct access jwt token.
            """)
    void getNewAccessToken_when_tokenIsNotCorrect() throws Exception {
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(null);
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(jwtTokenRequest);

        mockMvc.perform(put("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName(value = """
            getNewAccessToken(): Return status 400.
            If not found user with login from access jwt token not found.
            """)
    void getNewAccessToken_when_tokenCorrect_and_notFoundUserByLogin() throws Exception {
        UserDTO userDTO = userService.findByLogin("user_2");
        log.log(DEBUG, userService.deleteById(userDTO.getId()));

        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest(userJwtToken);
        String createJwtTokenRequestJson = objectMapper.writeValueAsString(jwtTokenRequest);

        mockMvc.perform(put("/api/v2/auth/token")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(createJwtTokenRequestJson))
                .andDo(result -> log.log(DEBUG, result.getResponse().getContentAsString()))
                .andExpect(status().isBadRequest());
    }
}