package com.github.nekitos911.msuserservice.validator;

import com.github.nekitos911.msuserservice.validator.constraints.UserPassportConstraint;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PassportValidatorImpl implements ConstraintValidator<UserPassportConstraint, String> {
    @Override
    public boolean isValid(String passport, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isEmpty(passport) || passport.matches("\\d{4} \\d{6}");
    }
}
