package com.mjc.school.validation.dto.user;

import com.mjc.school.validation.annotation.IsExistsUserById;
import com.mjc.school.validation.annotation.IsNotExistsUserByLogin;
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
public class UserChangeLoginDto {
    @IsExistsUserById(message = "user_change_login_dto.user_id.not_valid.not_exists")
    private long userId;

    @NotNull(message = "user_change_login_dto.user_new_login.not_valid.null")
    @NotBlank(message = "user_change_login_dto.user_new_login.not_valid.is_blank")
    @Size(min = 4, max = 30, message = "user_change_login_dto.user_new_login.not_valid.size")
    @IsNotExistsUserByLogin(message = "user_change_login_dto.user_new_login.not_valid.exists")
    private String newLogin;
}