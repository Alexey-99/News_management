package com.mjc.school.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.config.language.Translator;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.util.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.mjc.school.name.LanguageLocale.getLocale;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final Translator translator;
    private final DateFormatter dateHandler;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        response.setHeader("Content-Type", "application/json");
        response.setStatus(SC_UNAUTHORIZED);

        String headerLang = request.getHeader(ACCEPT_LANGUAGE);
        translator.setLocale(getLocale(headerLang));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(UNAUTHORIZED.value())
                .errorMessage(translator.toLocale("exception.access_without_authorization"))
                .timestamp(dateHandler.getCurrentDate())
                .build();

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, new ResponseEntity<>(errorResponse, UNAUTHORIZED));
        out.flush();
    }
}