package com.mjc.school.controller;

import com.mjc.school.exception.ErrorResponse;
import com.mjc.school.util.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class DefaultHomeController {
    private final DateFormatter dateHandler;

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