package com.mjc.school.util;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Component
public class DateFormatter {
    public String getCurrentDate() {
        return now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }
}