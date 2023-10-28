package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDto {
    @JsonIgnore
    private long id;
    private String login;
    private String password;
    private String email;
    @Min(value = 1)
    @Max(value = 2)
    private long roleId;
}