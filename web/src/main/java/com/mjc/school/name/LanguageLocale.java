package com.mjc.school.name;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;

@AllArgsConstructor
@Getter
public enum LanguageLocale {
    RU(new Locale("ru")),
    EN(new Locale("en"));


    private final Locale locale;

    public static Locale getLocale(String locale) {
        Locale currentLocale = null;
        if (locale != null && !locale.isBlank()) {
            for (LanguageLocale languageLocale : LanguageLocale.values()) {
                if (languageLocale.getLocale().getLanguage().equalsIgnoreCase(locale)) {
                    currentLocale = languageLocale.getLocale();
                }
            }
        }
        return currentLocale != null ? currentLocale : EN.getLocale();
    }
}