package com.mjc.school.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjc.school.config.language.Translator;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.exception.ServiceException;
import com.mjc.school.handler.DateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private final Translator translator;
    private final DateHandler dateHandler;

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleServiceExceptions(ServiceException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(NOT_FOUND.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(MethodArgumentNotValidException ex) {
        String details = translator.toLocale(ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(UNAUTHORIZED.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = translator.toLocale("exception.badRequest");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        String details = translator.toLocale("exception.noHandler");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(NOT_FOUND.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        String details = translator.toLocale("exception.notSupported");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(METHOD_NOT_ALLOWED.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}