package com.github.nekitos911.msuserservice.exception;

import com.github.nekitos911.msuserservice.enums.ErrorCode;
import com.github.nekitos911.msuserservice.model.GenericError;
import com.github.nekitos911.msuserservice.model.GenericResponse;
import com.github.nekitos911.msuserservice.model.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({SourceHeaderValidationException.class})
    public ResponseEntity<GenericResponse<Void>> handleValidation(SourceHeaderValidationException e) {
        log.error("Missing required params", e);

        return ResponseEntity.badRequest().body(GenericResponse.ofError(new GenericError(
                new ServiceError(ErrorCode.SOURCE_HEADER_VALIDATION_ERROR, e.getException().getMessage()))));
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<GenericResponse<Void>> handleHeader(MissingRequestHeaderException e) {
        log.error("Missing header", e);

        if (e.getCause() != null && e.getCause().getClass().isInstance(HeaderNotValidException.class)) {
            return handleHeaderNotValidException((HeaderNotValidException)e.getCause());
        }

        return ResponseEntity.badRequest().body(GenericResponse.ofError(new GenericError(
                new ServiceError(ErrorCode.MISSING_REQUIRED_HEADER, "Missing required header " + e.getHeaderName()))));
    }

    private Optional<ResponseEntity<GenericResponse<Void>>> handleHeader(Exception e) {
        return Optional.ofNullable(e)
                .filter(ex -> ex.getCause() != null)
                .map(Throwable::getCause)
                .filter(cause -> cause instanceof ConversionFailedException)
                .map(Throwable::getCause)
                .filter(cause -> cause instanceof HeaderNotValidException)
                .map(ex -> handleHeaderNotValidException((HeaderNotValidException)ex));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<GenericResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
        log.error("validation failed", e);

        Map<String, String> errors = e.getBindingResult().getAllErrors().stream().collect(Collectors
                .toMap(er -> ((FieldError) er).getField(), er -> Optional.ofNullable(er.getDefaultMessage()).orElse("")));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericResponse.ofError(new GenericError(
                new ServiceError(ErrorCode.FIELDS_VALIDATION_ERROR, errors.toString()))));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<GenericResponse<Void>> handleUserException(UserNotFoundException e) {
        log.error("User not found", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponse.ofError(new GenericError(
                new ServiceError(ErrorCode.USER_NOT_FOUND, e.getMessage()))));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericResponse<Void>> handleException(Exception e) {
        return handleHeader(e)
                .orElseGet(() -> {
                    log.error("Internal server error", e);

                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponse.ofError(new GenericError(
                            new ServiceError(ErrorCode.GENERAL_ERROR, "Internal server error"))));
                });
    }

    private ResponseEntity<GenericResponse<Void>> handleHeaderNotValidException(HeaderNotValidException e) {
        log.error("Header is not valid", e);

        return ResponseEntity.badRequest().body(GenericResponse.ofError(new GenericError(
                new ServiceError(ErrorCode.SOURCE_HEADER_NOT_VALID, e.getMessage()))));
    }

    @ExceptionHandler({EmptySearchParamsException.class})
    public ResponseEntity<GenericResponse<Void>> handleEmptySearchParams(EmptySearchParamsException e) {
        log.error("empty params", e);

        return ResponseEntity.badRequest().body(GenericResponse.ofError(new GenericError(
                new ServiceError(ErrorCode.EMPTY_SEARCH_PARAMS, e.getMessage()))));
    }
}
