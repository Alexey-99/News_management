package com.mjc.school.config.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private Locale locale;
    @Autowired
    private ResourceBundleMessageSource messageSource;

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String toLocale(String msg) {
        return messageSource.getMessage(msg, null, locale);
    }
}