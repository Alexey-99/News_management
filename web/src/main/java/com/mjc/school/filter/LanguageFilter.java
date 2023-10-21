package com.mjc.school.filter;

import com.mjc.school.config.language.Translator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import static com.mjc.school.name.LanguageLocale.getLocale;
import static org.apache.logging.log4j.Level.INFO;

@RequiredArgsConstructor
@WebFilter(urlPatterns = {
        "/api/v2/news/*",
        "/api/v2/author/*",
        "/api/v2/comment/*",
        "/api/v2/tag/*"})
public class LanguageFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    private final Translator translator;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String headerLang = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        translator.setLocale(getLocale(headerLang));
        log.log(INFO, "Request URI is: " + req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        log.log(INFO, "Response Status Code is: " + ((HttpServletResponse) servletResponse).getStatus());
    }
}