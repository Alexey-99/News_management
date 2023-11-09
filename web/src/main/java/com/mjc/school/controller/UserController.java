package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "/api/v2/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration") //TODO all
    public ResponseEntity<Boolean> createUser(@Valid
                                              @RequestBody
                                              RegistrationUserDto registrationUserDto) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(userService.create(registrationUserDto), CREATED);
    }

    @PatchMapping("/role") //TODO amdin
    public ResponseEntity<Boolean> changeRole(@Valid
                                              @RequestBody
                                              UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(userService.changeRole(userChangeRoleDto), OK);
    }
}