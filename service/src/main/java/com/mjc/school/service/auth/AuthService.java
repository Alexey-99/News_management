package com.mjc.school.service.auth;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;

public interface AuthService {
    String createAuthToken(CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException;
}