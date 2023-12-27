package com.mjc.school.controller;

import com.mjc.school.exception.CustomAccessDeniedException;
import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.JwtTokenValidator;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping(value = "/api/v2/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/token")
    public ResponseEntity<JwtTokenResponse> createAuthToken(@Valid
                                                            @RequestBody
                                                            @NotNull
                                                            CreateJwtTokenRequest authRequest)
            throws ServiceBadRequestParameterException {
        String token = authService.createAuthToken(authRequest);
        return new ResponseEntity<>(
                JwtTokenResponse.builder()
                        .token(token)
                        .userRole(jwtTokenUtil.getRoles(token).get(0))
                        .build(),
                CREATED);
    }

    @PostMapping("/token/valid/user")
    public ResponseEntity<Boolean> isValidAuthTokenUser(@RequestBody
                                                        ValidationJwtToken jwtToken)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        boolean result = jwtTokenValidator.isUserRoleUser(jwtToken);
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping("/token/valid/admin")
    public ResponseEntity<Boolean> isValidAuthTokenAdmin(@RequestBody
                                                         ValidationJwtToken jwtToken)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        boolean result = jwtTokenValidator.isUserRoleAdmin(jwtToken);
        return new ResponseEntity<>(result, OK);
    }
}