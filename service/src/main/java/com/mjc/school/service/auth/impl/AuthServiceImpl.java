package com.mjc.school.service.auth.impl;

import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.service.auth.AuthService;
import com.mjc.school.service.user.impl.CustomUserDetailsServiceImpl;
import com.mjc.school.util.JwtTokenUtil;
import com.mjc.school.validation.dto.jwt.JwtRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final CustomUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String createAuthToken(JwtRequest authRequest) throws ServiceBadRequestParameterException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUserName(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ServiceBadRequestParameterException("Не корректный логин или пароль");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
        return jwtTokenUtil.generateToken(userDetails);
    }
}