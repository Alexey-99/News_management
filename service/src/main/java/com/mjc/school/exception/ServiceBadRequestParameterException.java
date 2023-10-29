package com.mjc.school.exception;

public class ServiceBadRequestParameterException extends Exception {
    public ServiceBadRequestParameterException() {
    }

    public ServiceBadRequestParameterException(String message) {
        super(message);
    }

    public ServiceBadRequestParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceBadRequestParameterException(Throwable cause) {
        super(cause);
    }
}