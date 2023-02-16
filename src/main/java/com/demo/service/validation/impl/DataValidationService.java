package com.demo.service.validation.impl;

import com.demo.service.dto.DataDto;
import com.demo.service.validation.ValidationService;
import com.demo.service.validation.validator.AtomicValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataValidationService implements ValidationService<DataDto> {

    private final List<AtomicValidator<DataDto>> validators;

    @Override
    public void validate(DataDto dataDto) {
        validators.forEach(v -> v.validate(dataDto));
    }
}
