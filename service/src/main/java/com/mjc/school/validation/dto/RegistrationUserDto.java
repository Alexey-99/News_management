package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String login;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3)
    private String confirmPassword;

    private String email;

    @JsonIgnore
    //@IsExistsRoleById
    private long roleId;
}