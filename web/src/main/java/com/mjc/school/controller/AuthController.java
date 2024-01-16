package com.mjc.school.controller;

import com.mjc.school.exception.CustomAccessDeniedException;
import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> createJwtToken(@Valid
                                                           @RequestBody
                                                           @NotNull
                                                           CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(authService.createAuthToken(authRequest), CREATED);
    }

    @PutMapping("/token")
    public ResponseEntity<JwtTokenResponse> getNewAccessToken(@Valid
                                                              @RequestBody
                                                              @NotNull
                                                              JwtTokenRequest jwtTokenRequest) throws ServiceBadRequestParameterException {
        JwtTokenResponse response = authService.getAccessToken(jwtTokenRequest.getAccessToken());
        return new ResponseEntity<>(response, OK);
    }

    @PostMapping("/token/valid/user")
    public ResponseEntity<Boolean> isValidAuthTokenUser(@RequestBody
                                                        ValidationJwtToken jwtToken)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        boolean result = jwtTokenUtil.isUser(jwtToken);
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping("/token/valid/admin")
    public ResponseEntity<Boolean> isValidAuthTokenAdmin(@RequestBody
                                                         ValidationJwtToken jwtToken)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        boolean result = jwtTokenUtil.isAdmin(jwtToken);
        return new ResponseEntity<>(result, OK);
    }
}