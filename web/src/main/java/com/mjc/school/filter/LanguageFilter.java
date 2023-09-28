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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

@WebFilter(urlPatterns = {"/news/*", "/author/*", "/comment/*", "/tag/*"})
public class LanguageFilter implements Filter {
    private static final Logger log = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String headerLang = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        Translator.setLocale(LanguageLocale.getLocale(headerLang));

        log.log(Level.INFO, "Request URI is: ", req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        log.log(Level.INFO, "Response Status Code is: ", ((HttpServletResponse) servletResponse).getStatus());
    }
}