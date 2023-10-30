package com.mjc.school.service.auth.impl;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.CreateJwtTokenRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static org.apache.logging.log4j.Level.ERROR;

@Log4j2
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String createAuthToken(CreateJwtTokenRequest authRequest) throws ServiceBadRequestParameterException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(),
                    authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
            return jwtTokenUtil.generateToken(userDetails);
        } catch (BadCredentialsException e) {
            log.log(ERROR, "Login or password was entered incorrectly.");
            throw new ServiceBadRequestParameterException("service.exception.create_auth_token.incorrect_password_or_login");
        }
    }
}