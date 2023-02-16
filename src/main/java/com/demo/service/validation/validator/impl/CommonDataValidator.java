package com.demo.service.validation.validator.impl;

import com.demo.service.dto.DataDto;
import com.demo.service.exception.ValidationException;
import com.demo.service.validation.validator.AtomicValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommonDataValidator implements AtomicValidator<DataDto> {

    private final Validator validator;

    @Override
    public void validate(DataDto obj) {
        Set<ConstraintViolation<DataDto>> result = validator.validate(obj);

        if (!result.isEmpty()) {
            throw new ValidationException(
                    result.stream()
                            .map(v -> new ValidationException.Violation(v.getPropertyPath().toString(), v.getMessage()))
                            .toList()
            );
        }
    }
}
