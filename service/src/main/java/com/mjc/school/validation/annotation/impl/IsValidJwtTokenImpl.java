package com.mjc.school.validation.annotation.impl;

import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.annotation.IsValidJwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.Level.INFO;

@Log4j2
@RequiredArgsConstructor
public class IsValidJwtTokenImpl implements ConstraintValidator<IsValidJwtToken, String> {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        String userName;
        if (token != null) {
            try {
                userName = jwtTokenUtil.getUserName(token);
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
