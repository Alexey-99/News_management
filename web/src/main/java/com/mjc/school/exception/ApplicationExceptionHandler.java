package com.mjc.school.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjc.school.config.language.Translator;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.mjc.school.exception.ExceptionCodes.METHOD_NOT_ALLOWED_EXCEPTION;
import static com.mjc.school.exception.ExceptionCodes.NOT_FOUND_EXCEPTION;
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.BAD_REQUEST_PARAMETER;
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.NOT_SUPPORTED;
import static com.mjc.school.exception.message.ExceptionIncorrectParameterMessage.NO_HANDLER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @Autowired
    private Translator translator;

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleServiceExceptions(ServiceException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = translator.toLocale(BAD_REQUEST_PARAMETER);
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        String details = translator.toLocale(NO_HANDLER);
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        String details = translator.toLocale(NOT_SUPPORTED);
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}