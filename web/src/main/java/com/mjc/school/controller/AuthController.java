package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<JwtTokenResponse> createAuthToken(@Valid
                                                            @RequestBody
                                                            CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(new JwtTokenResponse(authService.createAuthToken(authRequest)), CREATED);
    }
}