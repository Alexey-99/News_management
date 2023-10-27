package com.mjc.school.config.language;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Setter
@Component
public class Translator {
    private Locale locale;
    private final ResourceBundleMessageSource messageSource;

    public String toLocale(String msg) {
        return messageSource.getMessage(msg, null, locale);
    }

    public String toLocale(String msg, Locale locale) {
        return messageSource.getMessage(msg, null, locale);
    }
}