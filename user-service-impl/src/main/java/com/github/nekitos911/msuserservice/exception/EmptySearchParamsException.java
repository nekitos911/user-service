package com.github.nekitos911.msuserservice.exception;

public class EmptySearchParamsException extends RuntimeException {
    private final static String MESSAGE = "Search params are empty";

    public EmptySearchParamsException() {
        super(MESSAGE);
    }
}
