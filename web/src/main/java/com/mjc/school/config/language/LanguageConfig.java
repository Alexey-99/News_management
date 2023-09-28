package com.mjc.school.config.language;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The type Language config.
 */
@Configuration
public class LanguageConfig extends AcceptHeaderLocaleResolver {
    private static final List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("ru"));

    /**
     * Method for getting current locale.
     *
     * @param request the request from user
     * @return current locale
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    /**
     * Create bean {@link ResourceBundleMessageSource} which will be used to get info from properties files.
     *
     * @return the message source
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        rs.setDefaultLocale(LOCALES.get(1));
        return rs;
    }
}