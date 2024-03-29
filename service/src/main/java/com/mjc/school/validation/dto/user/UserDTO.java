package com.mjc.school.validation.dto.user;

import com.mjc.school.validation.dto.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String login;
    private RoleDTO role;
}