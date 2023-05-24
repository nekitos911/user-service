package com.github.nekitos911.msuserservice.validator.constraints;

import com.github.nekitos911.msuserservice.validator.EmailValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidatorImpl.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserEmailConstraint {
    String message() default "Invalid passport number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}