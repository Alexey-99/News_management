package com.mjc.school.validation.dto.user;

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
public class RegistrationUserDto {
    @NotNull(message = "registration_user_dto.login.not_valid.null")
    @NotBlank(message = "registration_user_dto.login.not_valid.is_blank")
    @Size(min = 3, max = 30, message = "registration_user_dto.login.not_valid.size")
    @IsNotExistsUserByLogin(message = "registration_user_dto.login.not_valid.exists")
    private String login;

    @NotNull(message = "registration_user_dto.password.not_valid.null")
    @NotBlank(message = "registration_user_dto.password.not_valid.is_blank")
    @Size(min = 4, max = 30, message = "registration_user_dto.password.not_valid.size")
    private String password;

    @NotNull(message = "registration_user_dto.confirm_password.not_valid.null")
    @NotBlank(message = "registration_user_dto.confirm_password.not_valid.is_blank")
    @Size(min = 4, max = 30, message = "registration_user_dto.confirm_password.not_valid.size")
        private String confirmPassword;
}