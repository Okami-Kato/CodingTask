package com.demo.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ValidationException extends RuntimeException {

    private final List<Violation> violations;

    public record Violation(String property, String message){

    }
}
