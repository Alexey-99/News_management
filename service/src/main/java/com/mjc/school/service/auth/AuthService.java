package com.mjc.school.service.auth;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.validation.dto.jwt.JwtRequest;

public interface AuthService {
    String createAuthToken(JwtRequest authRequest) throws ServiceBadRequestParameterException;
}