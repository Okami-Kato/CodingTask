package com.demo.service.validation.validator.impl;

import com.demo.repository.DataRepository;
import com.demo.service.dto.DataDto;
import com.demo.service.exception.ValidationException;
import com.demo.service.exception.ValidationException.Violation;
import com.demo.service.validation.validator.AtomicValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataExistsValidator implements AtomicValidator<DataDto> {

    private final DataRepository dataRepository;

    @Override
    public void validate(DataDto obj) {
        if (dataRepository.existsById(obj.getId())) {
            throw new ValidationException(
                    List.of(new Violation("id", "Data with id [%s] already exists".formatted(obj.getId())))
            );
        }
    }
}
