package com.mjc.school.service.user;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import com.mjc.school.validation.dto.user.UserDTO;

import java.util.List;

public interface UserService {
    boolean create(RegistrationUserDto userDto) throws ServiceBadRequestParameterException;

    boolean changeRole(UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException;

    List<UserDTO> findAll(int page, int size,
                          String sortingField, String sortingType) throws ServiceNoContentException;

    long countAllUsers();

    UserDTO findById(long id) throws ServiceNoContentException;

    UserDTO findByLogin(String login) throws ServiceNoContentException;

    List<UserDTO> findByRole(String role,
                             int page, int size,
                             String sortField, String sortType) throws ServiceNoContentException;

    long countAllUsersByRole(String role);

    Pagination<UserDTO> getPagination(List<UserDTO> elementsOnPage, long countAllElements, int page, int size);
}