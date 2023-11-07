package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/v2/auth/token")
    public ResponseEntity<JwtTokenResponse> createAuthToken(@Valid
                                                            @RequestBody
                                                            CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(new JwtTokenResponse(authService.createAuthToken(authRequest)), CREATED);
    }

//    @PostMapping("/api/v2/auth/token")
//    public ResponseEntity<JwtTokenResponse> createAuthToken(@Valid
//                                                            @RequestBody
//                                                            Principal principal) throws ServiceBadRequestParameterException {
//        return new ResponseEntity<>(new JwtTokenResponse(authService.createAuthToken(authRequest)), CREATED);
//    }

    @GetMapping("/code")
    public String code(@RequestParam(name = "code", required = false) String code,
                       @RequestParam(name = "state", required = false) String state) {

        System.out.println("_____________________code _______________" + code);
        return """
                _____________________OAuth2AuthenticationToken _______________
                """;
    }
}