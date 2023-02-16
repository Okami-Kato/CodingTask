package com.demo.controller.advice;

import com.demo.service.exception.NotFoundException;
import com.demo.service.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(), ex.getViolations()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(NotFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorResponse(Instant.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    private record ErrorResponse(Instant timestamp, Integer status, Object message) {

    }
}
