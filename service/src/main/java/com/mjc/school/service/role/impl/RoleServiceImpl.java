package com.mjc.school.service.role.impl;

import com.mjc.school.converter.RoleConverter;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.model.user.Role;
import com.mjc.school.repository.RoleRepository;
import com.mjc.school.service.role.RoleService;
import com.mjc.school.validation.dto.RoleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Override
    public List<RoleDTO> findAll() throws ServiceNoContentException {
        List<Role> roleList = roleRepository.findAllSortIdAsc();
        if (!roleList.isEmpty()) {
            return roleList.stream()
                    .map(roleConverter::toDTO)
                    .toList();
        } else {
            log.log(WARN, "Not found roles");
            throw new ServiceNoContentException();
        }
    }
}