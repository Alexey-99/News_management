package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "/api/v2/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Boolean> createUser(@Valid
                                              @RequestBody
                                              RegistrationUserDto registrationUserDto) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(userService.create(registrationUserDto), CREATED);
    }
}