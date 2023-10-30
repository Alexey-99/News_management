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
    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    private String login;

    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 5, message = "")
    private String password;

    @IsExistsRoleById(message = "")
    private long roleId;
}