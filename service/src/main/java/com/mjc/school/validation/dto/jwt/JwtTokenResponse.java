package com.mjc.school.validation.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private static final String TYPE = "Bearer";
    private String accessToken;
    private Date expiredDate;
    private String login;
    private String userRole;
}