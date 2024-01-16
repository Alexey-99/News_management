package com.mjc.school.validation.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Valid
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenRequest {
    private static final String TYPE = "Bearer";
    private String accessToken;
}