package com.mjc.school.validation.impl;

import com.mjc.school.exception.CustomAccessDeniedException;
import com.mjc.school.exception.CustomAuthenticationException;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.JwtTokenValidator;
import com.mjc.school.validation.dto.jwt.ValidationJwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import static com.mjc.school.model.user.User.UserRole.ROLE_ADMIN;
import static com.mjc.school.model.user.User.UserRole.ROLE_USER;
import static org.apache.logging.log4j.Level.INFO;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtTokenValidatorImpl implements JwtTokenValidator {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean isFitsRoleUser(ValidationJwtToken validationJwtToken)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        if (validationJwtToken != null && isCorrectJwtToken(validationJwtToken.getJwtToken())) {
            boolean result = !jwtTokenUtil.getRoles(validationJwtToken.getJwtToken())
                    .stream()
                    .filter(role -> role != null &&
                            (role.equalsIgnoreCase(ROLE_USER.name())
                                    || role.equalsIgnoreCase(ROLE_ADMIN.name())))
                    .toList()
                    .isEmpty();
            if (!result) {
                throw new CustomAccessDeniedException("exception.access_denied");
            }
        } else {
            throw new CustomAuthenticationException("exception.access_without_authorization");
        }
        return true;
    }

    @Override
    public boolean isFitsRoleAdmin(ValidationJwtToken validationJwtToken)
            throws CustomAuthenticationException, CustomAccessDeniedException {
        if (validationJwtToken != null && isCorrectJwtToken(validationJwtToken.getJwtToken())) {
            boolean result = !jwtTokenUtil.getRoles(validationJwtToken.getJwtToken())
                    .stream()
                    .filter(role -> role != null && role.equalsIgnoreCase(ROLE_ADMIN.name()))
                    .toList()
                    .isEmpty();
            if (!result) {
                throw new CustomAccessDeniedException("exception.access_denied");
            }
        } else {
            throw new CustomAuthenticationException("exception.access_without_authorization");
        }
        return true;
    }

    private boolean isCorrectJwtToken(String token) {
        boolean result = false;
        if (token != null) {
            try {
                String userName = jwtTokenUtil.getUserName(token);
                result = userName != null;
            } catch (ExpiredJwtException ex) {
                log.log(INFO, "Token lifetime has expired. Message: ".concat(ex.getMessage()));
            } catch (SignatureException ex) {
                log.log(INFO, "The signature is not correct. Message: ".concat(ex.getMessage()));
            } catch (UnsupportedJwtException ex) {
                log.log(INFO, "Unsupported jwt. Message: ".concat(ex.getMessage()));
            } catch (MalformedJwtException ex) {
                log.log(INFO, "Malformed jwt. Message: ".concat(ex.getMessage()));
            } catch (Exception ex) {
                log.log(INFO, "Invalid token. Message: ".concat(ex.getMessage()));
            }
        }
        return result;
    }
}
