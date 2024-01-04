package com.mjc.school.controller;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.NewsDTO;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import com.mjc.school.validation.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(value = "/api/v2/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Boolean> createUser(@Valid
                                              @RequestBody
                                              RegistrationUserDto registrationUserDto) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(userService.create(registrationUserDto), CREATED);
    }

    @PatchMapping("/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> changeRole(@Valid
                                              @RequestBody
                                              UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException {
        return new ResponseEntity<>(userService.changeRole(userChangeRoleDto), OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Pagination<UserDTO>> findAllUsers(@RequestAttribute(value = "size")
                                                            int size,
                                                            @RequestAttribute(value = "page")
                                                            int page,
                                                            @RequestParam(value = "sort-field", required = false)
                                                            String sortingField,
                                                            @RequestParam(value = "sort-type", required = false)
                                                            String sortingType) throws ServiceNoContentException {
        List<UserDTO> userDTOList;
        try {
            userDTOList = userService.findAll(page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                userDTOList = userService.findAll(page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(userService.getPagination(
                userDTOList, userService.countAllUsers(),
                page, size), OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> findById(@PathVariable
                                            @Min(value = 1,
                                                    message = "user_controller.path_variable.id.in_valid.min")
                                            long id) throws ServiceNoContentException {
        return new ResponseEntity<>(userService.findById(id), OK);
    }

    @GetMapping("/login/{login}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> findById(@PathVariable
                                            @NotNull(message = "user_controller.path_variable.login.in_valid.null")
                                            @NotBlank(message = "user_controller.path_variable.login.in_valid.is_blank")
                                            @Size(min = 3, max = 30,
                                                    message = "user_controller.path_variable.login.in_valid.size")
                                            String login) throws ServiceNoContentException {
        return new ResponseEntity<>(userService.findByLogin(login), OK);
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Pagination<UserDTO>> findByRole(@PathVariable
                                                          @NotNull(message = "user_controller.path_variable.role.in_valid.null")
                                                          String role,
                                                          @RequestAttribute(value = "size")
                                                          int size,
                                                          @RequestAttribute(value = "page")
                                                          int page,
                                                          @RequestParam(value = "sort-field", required = false)
                                                          String sortingField,
                                                          @RequestParam(value = "sort-type", required = false)
                                                          String sortingType) throws ServiceNoContentException {
        List<UserDTO> userDTOList;
        try {
            userDTOList = userService.findByRole(role, page, size, sortingField, sortingType);
        } catch (ServiceNoContentException ex) {
            if (page > 1) {
                page = 1;
                userDTOList = userService.findByRole(role, page, size, sortingField, sortingType);
            } else {
                throw new ServiceNoContentException();
            }
        }
        return new ResponseEntity<>(userService.getPagination(
                userDTOList, userService.countAllUsersByRole(role),
                page, size), OK);
    }
}