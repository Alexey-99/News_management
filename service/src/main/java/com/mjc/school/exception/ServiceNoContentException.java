package com.mjc.school.exception;

public class ServiceNoContentException extends Exception{
    public ServiceNoContentException() {
    }

    public ServiceNoContentException(String message) {
        super(message);
    }

    public ServiceNoContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNoContentException(Throwable cause) {
        super(cause);
    }
}
