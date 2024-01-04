package com.mjc.school.name;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;

@AllArgsConstructor
@Getter
public enum LanguageLocale {
    RU(new Locale[]{new Locale("ru"), new Locale("ru-Ru")}),
    EN(new Locale[]{new Locale("en"), new Locale("en-US")});


    private final Locale[] locales;

    public static Locale getLocale(String locale) {
        Locale currentLocale = null;
        if (locale != null && !locale.isBlank()) {
            for (LanguageLocale languageLocale : LanguageLocale.values()) {
                boolean isFoundLocale = !Arrays.stream(languageLocale.getLocales())
                        .filter(localeItem -> localeItem.getLanguage().equalsIgnoreCase(locale))
                        .toList()
                        .isEmpty();
                if (isFoundLocale) {
                    currentLocale = languageLocale.getLocales()[0];
                }
            }
        }
        return currentLocale != null ? currentLocale : EN.getLocales()[0];
    }
}