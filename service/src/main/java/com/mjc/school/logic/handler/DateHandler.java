package com.mjc.school.logic.handler;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Component
public class DateHandler {
    /**
     * Method for getting current date - format ISO 8601.
     *
     * @return current date of string type
     */
    public String getCurrentDate() {
        LocalDateTime localDateTime = now();
        return localDateTime.toString();
    }
}