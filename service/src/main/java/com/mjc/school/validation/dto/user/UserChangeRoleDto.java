package com.mjc.school.validation.dto.user;

import com.mjc.school.validation.annotation.IsExistsRoleById;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserChangeRoleDto {
    @NotNull(message = "user_change_role_dto.admin_login.not_valid.null")
    @NotBlank(message = "user_change_role_dto.admin_login.not_valid.is_blank")
    @Size(min = 3, message = "user_change_role_dto.admin_login.not_valid.size")
    private String adminLogin;

    @NotNull(message = "user_change_role_dto.admin_password.not_valid.null")
    @NotBlank(message = "user_change_role_dto.admin_password.not_valid.is_blank")
    @Size(min = 5, message = "user_change_role_dto.admin_password.not_valid.size")
    private String adminPassword;

    @NotNull(message = "user_change_role_dto.user_login.not_valid.null")
    @NotBlank(message = "user_change_role_dto.user_login.not_valid.is_blank")
    @Size(min = 3, message = "user_change_role_dto.user_login.not_valid.size")
    private String userLogin;

    @IsExistsRoleById(message = "")
    private long roleId;
}