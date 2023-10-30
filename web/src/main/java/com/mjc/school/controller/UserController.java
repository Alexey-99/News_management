package com.mjc.school.controller;

import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @PutMapping
    public ResponseEntity<RegistrationUserDto> update(@Valid @RequestBody RegistrationUserDto userDto) {
        return null;
    }
}