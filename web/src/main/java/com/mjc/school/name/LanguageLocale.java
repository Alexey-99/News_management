package com.mjc.school.name;

import java.util.Locale;

public enum LanguageLocale {
    RU(new Locale("ru")), EN(new Locale("en"));

    private Locale locale;

    private LanguageLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static Locale getLocale(String locale) {
        Locale currentLocale = null;
        if (locale != null && !locale.isBlank()) {
            for (LanguageLocale languageLocale : LanguageLocale.values()) {
                if (languageLocale.getLocale().getLanguage().equalsIgnoreCase(locale)) {
                    currentLocale = languageLocale.getLocale();
                }
            }
        }
        return currentLocale != null ? currentLocale : RU.getLocale();
    }
}