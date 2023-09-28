package com.mjc.school.filter;

import com.mjc.school.config.language.Translator;
import com.mjc.school.name.LanguageLocale;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import static org.apache.logging.log4j.Level.INFO;

@WebFilter(urlPatterns = {"/news/*", "/author/*", "/comment/*", "/tag/*"})
public class LanguageFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    @Autowired
    private Translator translator;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String headerLang = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        translator.setLocale(LanguageLocale.getLocale(headerLang));

        log.log(INFO, "Request URI is: " + req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        log.log(INFO, "Response Status Code is: " + ((HttpServletResponse) servletResponse).getStatus());
    }
}