package com.mjc.school.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mjc.school.config.language.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.mjc.school.name.LanguageLocale.getLocale;

@Component
@RequiredArgsConstructor
@WebFilter(urlPatterns = "api/v2/*")
public class LocaleFilter extends OncePerRequestFilter {
    private final Translator translator;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String headerLang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        translator.setLocale(getLocale(headerLang));
        filterChain.doFilter(request, response);
    }
}