package com.mjc.school.filter;

import com.mjc.school.config.language.Translator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.mjc.school.name.LanguageLocale.getLocale;
import static org.apache.logging.log4j.Level.INFO;

@Component
@RequiredArgsConstructor
public class LocaleFilter extends OncePerRequestFilter {
    private static final Logger log = LogManager.getLogger();
    private final Translator translator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String headerLang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        translator.setLocale(getLocale(headerLang));
        log.log(INFO, "Request URI is: " + request.getRequestURI());
        filterChain.doFilter(request, response);
        log.log(INFO, "Response Status Code is: " + response.getStatus());
    }
}