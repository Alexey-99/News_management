package com.mjc.school.config.language;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@WebFilter(urlPatterns = "/news/*")
public class LanguageFilter implements Filter {
    private static final List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("ru"));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String headerLang = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (headerLang == null || headerLang.isBlank() || !isCorrectLang(headerLang)) {
            res.setHeader("header", "value");
        }

        System.out.println("Request URI is: " + req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Response Status Code is: " + res.getStatus());
        System.out.println("res.getHeaderNames()" + res.getHeaderNames());
    }

    private boolean isCorrectLang(String headerLang) {
        return LOCALES.stream()
                .filter(locale -> !locale.getLanguage().equals(headerLang))
                .toList()
                .isEmpty();
    }
}