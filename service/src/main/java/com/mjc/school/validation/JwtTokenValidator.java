package com.mjc.school.validation;

import com.mjc.school.exception.CustomAccessDeniedException;
import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;

public interface JwtTokenValidator {
    boolean isUserRoleUser(ValidationJwtToken token)
            throws CustomAuthenticationException, CustomAccessDeniedException;

    boolean isUserRoleAdmin(ValidationJwtToken token)
            throws CustomAuthenticationException, CustomAccessDeniedException;
}
