package com.demo.service.validation.validator;

public interface AtomicValidator<T> {

    void validate(T obj);
}
