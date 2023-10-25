package com.mjc.school.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

//@RequiredArgsConstructor
//@Component
public class CustomAccessDeniedHandlerImpl/* implements AccessDeniedHandler*/ {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private static final int ACCESS_DENIED = 40301;

    private  MessageSource messageSource;

//    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> body = new HashMap<>();
        String message = messageSource.getMessage("access_denied", null,
                "Access denied", LocaleContextHolder.getLocale());
        body.put(ERROR_MESSAGE, message);
        body.put(ERROR_CODE, ACCESS_DENIED);

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, new ResponseEntity<>(body, HttpStatus.FORBIDDEN));
        out.flush();
    }
}