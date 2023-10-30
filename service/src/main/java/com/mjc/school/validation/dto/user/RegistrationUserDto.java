package com.mjc.school.validation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RegistrationUserDto {
    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    private String login;

    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    private String password;

    @NotNull(message = "")
    @NotBlank(message = "")
    @Size(min = 3, message = "")
    private String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "")
    private String email;
}