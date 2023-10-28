package com.mjc.school.controller;

import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.service.user.UserService;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.JwtRequest;
import com.mjc.school.validation.dto.jwt.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v2/auth")
public class AuthController {
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> createAuthToken(@Valid @RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Не корректный логин или пароль");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }
}