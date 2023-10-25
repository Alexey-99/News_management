package com.mjc.school.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

//@Component
//@RequiredArgsConstructor
public class CustomAuthenticationEntryPointImpl /*implements AuthenticationEntryPoint */ {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private static final int UNAUTHORIZED = 40101;

    private MessageSource messageSource;

    //@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        String message = messageSource.getMessage("access_without_authorization", null,
                "This action requires authorization", LocaleContextHolder.getLocale());
        body.put(ERROR_MESSAGE, message);
        body.put(ERROR_CODE, UNAUTHORIZED);

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED));
        out.flush();
    }
}
