package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long roleId;
}