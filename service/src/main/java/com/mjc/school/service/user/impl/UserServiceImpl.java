package com.mjc.school.service.user.impl;

import com.mjc.school.converter.UserConverter;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.user.User;
import com.mjc.school.repository.UserRepository;
import com.mjc.school.service.pagination.PaginationService;
import com.mjc.school.service.user.UserService;
import com.mjc.school.validation.dto.Pagination;
import com.mjc.school.validation.dto.user.RegistrationUserDto;
import com.mjc.school.validation.dto.user.UserChangeLoginDto;
import com.mjc.school.validation.dto.user.UserChangeRoleDto;
import com.mjc.school.validation.dto.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static com.mjc.school.service.user.impl.sort.UserSortField.LOGIN;
import static com.mjc.school.service.user.impl.sort.UserSortField.ROLE;
import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final PaginationService paginationService;

    @Override
    @Transactional
    public boolean create(RegistrationUserDto userDto) throws ServiceBadRequestParameterException {
        if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
            if (userRepository.notExistsByLogin(userDto.getLogin())) {
                User user = userConverter.fromRegistrationUserDTO(userDto);
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                userRepository.save(user);
                return true;
            } else {
                log.log(WARN, "Exists user with login: " + userDto.getLogin());
                throw new ServiceBadRequestParameterException("service.exception.registration.login.not_valid.exists");
            }
        } else {
            log.log(ERROR, "The entered passwords do not match.");
            throw new ServiceBadRequestParameterException("service.exception.registration.passwords_not_match");
        }
    }

    @Override
    @Transactional
    public boolean changeRole(UserChangeRoleDto userChangeRoleDto) throws ServiceBadRequestParameterException {
        if (userRepository.existsByLogin(userChangeRoleDto.getUserLogin())) {
            userRepository.changeRole(userChangeRoleDto.getUserLogin(), userChangeRoleDto.getRoleId());
            return true;
        } else {
            log.log(WARN, "Not found user with login " + userChangeRoleDto.getUserLogin());
            throw new ServiceBadRequestParameterException("service.exception.change_role.user_login.not_valid.not_exists");
        }
    }

    @Override
    @Transactional
    public boolean changeLogin(UserChangeLoginDto userChangeLoginDto) throws ServiceBadRequestParameterException {
        if (userRepository.existsById(userChangeLoginDto.getUserId())) {
            if (userRepository.notExistsByLogin(userChangeLoginDto.getNewLogin())) {
                userRepository.changeLogin(userChangeLoginDto.getUserId(), userChangeLoginDto.getNewLogin());
                return true;
            } else {
                log.log(WARN, "Exists user with login: {}", userChangeLoginDto.getNewLogin());
                throw new ServiceBadRequestParameterException("service.exception.change_login.user_new_login.not_valid.exists");
            }
        } else {
            log.log(WARN, "Not found user with id: {}", userChangeLoginDto.getUserId());
            throw new ServiceBadRequestParameterException("service.exception.change_login.user_id.not_valid.not_exists");
        }
    }

    @Transactional
    @Override
    public boolean deleteById(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            log.log(WARN, "Not found user with id: {}", userId);
        }
        return true;
    }

    @Override
    public List<UserDTO> findAll(int page, int size,
                                 String sortField, String sortType) throws ServiceNoContentException {
        List<User> userList;
        if (sortField != null && sortField.equalsIgnoreCase(LOGIN.name())) {
            userList = findAllSortLogin(page, size, sortType);
        } else if (sortField != null && sortField.equalsIgnoreCase(ROLE.name())) {
            userList = findAllSortRole(page, size, sortType);
        } else {
            userList = findAllSortId(page, size, sortType);
        }
        if (!userList.isEmpty()) {
            return userList.stream()
                    .map(userConverter::toUserDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found users");
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllUsers() {
        return userRepository.countAllUsers();
    }

    @Override
    public UserDTO findById(long id) throws ServiceNoContentException {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.log(WARN, "Not found user by ID: {}", id);
            return new ServiceNoContentException();
        });
        return userConverter.toUserDTO(user);
    }

    @Override
    public UserDTO findByLogin(String login) throws ServiceNoContentException {
        User user = userRepository.findByLogin(login).orElseThrow(() -> {
            log.log(WARN, "Not found user by login: {}", login);
            return new ServiceNoContentException();
        });
        return userConverter.toUserDTO(user);
    }

    @Override
    public List<UserDTO> findByRole(String role,
                                    int page, int size,
                                    String sortField, String sortType) throws ServiceNoContentException {
        List<User> userList;
        if (sortField != null && sortField.equalsIgnoreCase(LOGIN.name())) {
            userList = findByRoleSortLogin(role, page, size, sortType);
        } else {
            userList = findByRoleSortId(role, page, size, sortType);
        }
        if (!userList.isEmpty()) {
            return userList.stream()
                    .map(userConverter::toUserDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found users");
            throw new ServiceNoContentException();
        }
    }

    @Override
    public long countAllUsersByRole(String role) {
        return userRepository.countAllUsersByRole("%" + role + "%");
    }

    @Override
    public Pagination<UserDTO> getPagination(List<UserDTO> elementsOnPage, long countAllElements, int page, int size) {
        return Pagination
                .<UserDTO>builder()
                .entity(elementsOnPage)
                .size(size)
                .countAllEntity(countAllElements)
                .numberPage(page)
                .maxNumberPage(paginationService.calcMaxNumberPage(countAllElements, size))
                .build();
    }

    private List<User> findAllSortLogin(int page, int size, String sortType) {
        List<User> userList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            userList = userRepository.findAllSortLoginAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            userList = userRepository.findAllSortLoginDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return userList;
    }

    private List<User> findAllSortRole(int page, int size, String sortType) {
        List<User> userList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            userList = userRepository.findAllSortRoleAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            userList = userRepository.findAllSortRoleDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return userList;
    }

    private List<User> findAllSortId(int page, int size, String sortType) {
        List<User> userList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            userList = userRepository.findAllSortIdAsc(size,
                    paginationService.calcNumberFirstElement(page, size));
        } else {
            userList = userRepository.findAllSortIdDesc(size,
                    paginationService.calcNumberFirstElement(page, size));
        }
        return userList;
    }

    private List<User> findByRoleSortLogin(String roleName, int page, int size, String sortType) {
        List<User> userList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            userList = userRepository.findByRoleSortLoginAsc("%" + roleName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            userList = userRepository.findByRoleSortLoginDesc("%" + roleName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return userList;
    }

    private List<User> findByRoleSortId(String roleName, int page, int size, String sortType) {
        List<User> userList;
        if (sortType != null && sortType.equalsIgnoreCase(ASC.name())) {
            userList = userRepository.findByRoleSortIdAsc("%" + roleName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        } else {
            userList = userRepository.findByRoleSortIdDesc("%" + roleName + "%",
                    size, paginationService.calcNumberFirstElement(page, size));
        }
        return userList;
    }
}