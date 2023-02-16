package com.demo.service.validation;

public interface ValidationService<T> {

    void validate(T dataDto);
}
