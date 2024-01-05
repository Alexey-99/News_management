package com.mjc.school.service.role;

import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.validation.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAll() throws ServiceNoContentException;
}
