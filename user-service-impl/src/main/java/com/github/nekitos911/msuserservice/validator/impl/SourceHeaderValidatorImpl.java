package com.github.nekitos911.msuserservice.validator.impl;

import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.enums.Source;
import com.github.nekitos911.msuserservice.exception.SourceHeaderValidationException;
import com.github.nekitos911.msuserservice.validator.SourceHeaderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class SourceHeaderValidatorImpl implements SourceHeaderValidator {
    private final Validator validator;

    @Override
    public void validate(Source source, UserDto userDto) {
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(userDto, source.getValidators());

        if (!constraintViolations.isEmpty()) throw new SourceHeaderValidationException(new ConstraintViolationException(constraintViolations));
    }
}
