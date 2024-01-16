package com.mjc.school.service.auth;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;

public interface AuthService {
    JwtTokenResponse createAuthToken(CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException;

    JwtTokenResponse getAccessToken(String refreshToken) throws ServiceBadRequestParameterException;
}