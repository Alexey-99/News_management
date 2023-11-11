package com.mjc.school.controller;

import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.handler.DateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
public class DefaultController {
    private final DateHandler dateHandler;

    @GetMapping
    public ResponseEntity<ErrorResponse> error404() {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorMessage("Everything happened is not so")
                        .errorCode(NOT_FOUND.value())
                        .timestamp(dateHandler.getCurrentDate())
                        .build(),
                NOT_FOUND);
    }
}