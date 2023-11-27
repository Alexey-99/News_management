package com.mjc.school.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjc.school.config.language.Translator;
import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.exception.ServiceBadRequestParameterException;
import com.mjc.school.exception.ServiceNoContentException;
import com.mjc.school.handler.DateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestControllerAdvice
public class ApplicationExceptionHandler {
    private final Translator translator;
    private final DateHandler dateHandler;

    @ExceptionHandler(ServiceBadRequestParameterException.class)
    public final ResponseEntity<Object> handleServiceBadRequestParameterException(ServiceBadRequestParameterException ex) {
        String details = translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    //   Status NO_CONTENT don't have body in response
    @ExceptionHandler(ServiceNoContentException.class)
    public final ResponseEntity<Object> handleServiceNoContentException() {
        return new ResponseEntity<>(NO_CONTENT);

    }

    @ExceptionHandler(ConstraintViolationException.class) // validation in controllers (@RequestParam (@Min) and other)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream().toList().get(0).getMessage();
        String details = translator.toLocale(message);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(BAD_REQUEST.value())
                .errorMessage(details)
                .timestamp(dateHandler.getCurrentDate())
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // validation DTO objects
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