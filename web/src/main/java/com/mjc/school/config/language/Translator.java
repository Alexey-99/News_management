package com.mjc.school.config.language;

import com.mjc.school.name.LanguageLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Translator.
 */
@Component
public class Translator {
    private static Locale locale;
    @Autowired
    private static ResourceBundleMessageSource messageSource;

    public static void setLocale(Locale locale) {
        Translator.locale = locale;
    }

    public static String toLocale(String msg) {
        return messageSource.getMessage(msg, null, locale);
    }
}