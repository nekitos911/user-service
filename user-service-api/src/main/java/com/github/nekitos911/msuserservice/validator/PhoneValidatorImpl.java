package com.github.nekitos911.msuserservice.validator;

import com.github.nekitos911.msuserservice.validator.constraints.UserPhoneConstraint;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidatorImpl implements ConstraintValidator<UserPhoneConstraint, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isEmpty(phone) || phone.matches("7\\d{10}");
    }
}
