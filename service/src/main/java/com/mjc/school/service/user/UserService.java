package com.mjc.school.service.user;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeLoginDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import com.mjc.school.validation.dto.user.UserDTO;

import javax.transaction.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    boolean create(RegistrationUserDto userDto) throws ServiceBadRequestParameterException;

    @Transactional
    boolean changeRole(UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException;

    @Transactional
    boolean changeLogin(UserChangeLoginDto userChangeLoginDto) throws ServiceBadRequestParameterException;

    @Transactional
    boolean deleteById(long userId);

    List<UserDTO> findAll(int page, int size,
                          String sortField, String sortType) throws ServiceNoContentException;

    long countAllUsers();

    UserDTO findById(long id) throws ServiceNoContentException;

    UserDTO findByLogin(String login) throws ServiceNoContentException;

    List<UserDTO> findByRole(String role,
                             int page, int size,
                             String sortField, String sortType) throws ServiceNoContentException;

    long countAllUsersByRole(String role);

    Pagination<UserDTO> getPagination(List<UserDTO> elementsOnPage, long countAllElements, int page, int size);
}