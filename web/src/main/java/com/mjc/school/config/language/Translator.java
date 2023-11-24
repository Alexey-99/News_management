package com.mjc.school.config.language;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class Translator {
    private final ResourceBundleMessageSource messageSource;
    private Locale locale;


    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String toLocale(String msg) {
        return messageSource.getMessage(msg, null, locale);
    }
}