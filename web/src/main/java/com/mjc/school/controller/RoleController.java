package com.mjc.school.controller;

import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.service.role.RoleService;
import com.mjc.school.validation.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "/api/v2/role")
@CrossOrigin
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RoleDTO>> findAll() throws ServiceNoContentException {
        return new ResponseEntity<>(roleService.findAll(), OK);
    }
}