package com.mjc.school.handler;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;

@Component
public class DateHandler {
    public String getCurrentDate() {
        return now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
}