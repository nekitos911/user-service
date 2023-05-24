package com.github.nekitos911.msuserservice.exception;

import lombok.Getter;

import javax.validation.ConstraintViolationException;

@Getter
public class SourceHeaderValidationException extends RuntimeException {
    private final ConstraintViolationException exception;
    public SourceHeaderValidationException(ConstraintViolationException ex) {
        exception = ex;
    }
}
