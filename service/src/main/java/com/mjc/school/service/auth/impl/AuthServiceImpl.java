package com.mjc.school.service.auth.impl;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import com.mjc.school.validation.dto.jwt.JwtTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.WARN;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public JwtTokenResponse createAuthToken(CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(),
                    authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());

            String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
            return JwtTokenResponse.builder()
                    .accessToken(accessToken)
                    .expiredDate(jwtTokenUtil.getExpirationAccessToken(accessToken))
                    .login(authRequest.getUserName())
                    .userRole(jwtTokenUtil.getRoles(accessToken).get(0))
                    .build();
        } catch (BadCredentialsException e) {
            log.log(ERROR, "Login or password was entered incorrectly.");
            throw new ServiceBadRequestParameterException("service.exception.create_auth_token.incorrect_password_or_login");
        }
    }

    @Override
    public JwtTokenResponse getAccessToken(String accessToken) throws ServiceBadRequestParameterException {
        if (jwtTokenUtil.validateAccessToken(accessToken)) {
            String login = jwtTokenUtil.getUserNameAccessToken(accessToken);
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(login);
                accessToken = jwtTokenUtil.generateAccessToken(userDetails);
                return JwtTokenResponse.builder()
                        .accessToken(accessToken)
                        .expiredDate(jwtTokenUtil.getExpirationAccessToken(accessToken))
                        .build();
            } catch (UsernameNotFoundException e) {
                log.log(WARN, "Not found user with login: {}", login);
                throw new ServiceBadRequestParameterException("service.exception.user_not_exists");
            }
        } else {
            log.log(WARN, "Jwt token not valid: token = {}", accessToken);
            throw new ServiceBadRequestParameterException("service.exception.create_new_access_jwt_token.incorrect_access_jwt_token");
        }
    }
}