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
    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    @IsExistsUserByLogin(message = "")
    private String userName;

    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    private String password;
}