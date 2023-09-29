package com.mjc.school.exception;

import java.security.PrivilegedActionException;

public class IncorrectParameterException extends Exception {
    public IncorrectParameterException() {
    }

    public IncorrectParameterException(String message) {
        super(message);
    }

    public IncorrectParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectParameterException(Throwable cause) {
        super(cause);
    }
}