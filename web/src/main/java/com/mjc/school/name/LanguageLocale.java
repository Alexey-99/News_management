package com.mjc.school.name;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum LanguageLocale {
    RU(new Locale("ru")), EN(new Locale("en"));

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