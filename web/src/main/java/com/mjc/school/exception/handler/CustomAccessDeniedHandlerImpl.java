package com.mjc.school.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.config.language.Translator;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.handler.DateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.mjc.school.name.LanguageLocale.getLocale;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final Translator translator;
    private final DateHandler dateHandler;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.setStatus(SC_FORBIDDEN);

        String headerLang = request.getHeader(ACCEPT_LANGUAGE);
        translator.setLocale(getLocale(headerLang));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(FORBIDDEN.value())
                .errorMessage(translator.toLocale("exception.access_denied"))
                .timestamp(dateHandler.getCurrentDate())
                .build();

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, new ResponseEntity<>(errorResponse, FORBIDDEN));
        out.flush();
    }
}
