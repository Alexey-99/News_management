package com.mjc.school.validation.dto.jwt;

import com.mjc.school.validation.annotation.IsExistsUserByLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateJwtTokenRequest {
    @NotNull(message = "create_jwt_token_request.user_name.not_valid.null")
    @NotBlank(message = "create_jwt_token_request.user_name.not_valid.is_blank")
    @Size(min = 3, max = 30, message = "create_jwt_token_request.user_name.not_valid.size")
    @IsExistsUserByLogin(message = "create_jwt_token_request.user_name.not_valid.user_not_exists_by_login")
    private String userName;

    @NotNull(message = "create_jwt_token_request.password.not_valid.null")
    @NotBlank(message = "create_jwt_token_request.password.not_valid.is_blank")
    @Size(min = 4, max = 30, message = "create_jwt_token_request.password.not_valid.size")
    private String password;
}