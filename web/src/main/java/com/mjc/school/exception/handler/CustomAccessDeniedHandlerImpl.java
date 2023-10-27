package com.mjc.school.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.school.config.language.Translator;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.name.LanguageLocale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static com.mjc.school.name.LanguageLocale.getLocale;

@Log4j2
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final Translator translator;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.info("________________________________CustomAccessDeniedHandlerImpl__________________________________");

        response.setHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String headerLang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        ErrorResponse errorResponse = new ErrorResponse(401, translator.toLocale("UNAUTHORIZED", getLocale(headerLang)));

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorResponse);
        out.flush();

//        Map<String, Object> body = new HashMap<>();
//        String message = messageSource.getMessage("access_denied", null,
//                "Access denied", LocaleContextHolder.getLocale());
//        body.put(ERROR_MESSAGE, message);
//        body.put(ERROR_CODE, ACCESS_DENIED);
//
//        OutputStream out = response.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(out, new ResponseEntity<>(body, HttpStatus.FORBIDDEN));
//        out.flush();
    }
}