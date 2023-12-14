package com.mjc.school.validation.dto.user;

import com.mjc.school.validation.annotation.IsExistsRoleById;
import com.mjc.school.validation.annotation.IsExistsUserByLogin;
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
    @NotNull(message = "user_change_role_dto.user_login.not_valid.null")
    @NotBlank(message = "user_change_role_dto.user_login.not_valid.is_blank")
    @Size(min = 4, max = 30, message = "user_change_role_dto.user_login.not_valid.size")
    @IsExistsUserByLogin(message = "user_change_role_dto.user_login.not_valid.not_exists")
    private String userLogin;

    @IsExistsRoleById(message = "user_change_role_dto.role_id.not_valid.not_exists")
    private long roleId;
}