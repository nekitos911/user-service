package com.github.nekitos911.msuserservice.exception;

public class HeaderNotValidException extends RuntimeException {
    private static final String MESSAGE = "Header [%s] is not valid";

    public HeaderNotValidException(String headerName) {
        super(String.format(MESSAGE, headerName));
    }
}
