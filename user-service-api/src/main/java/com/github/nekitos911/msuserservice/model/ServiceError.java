package com.github.nekitos911.msuserservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nekitos911.msuserservice.enums.ErrorCode;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@Getter
@FieldNameConstants
public class ServiceError {
    private final ErrorCode errorCode;
    private final String message;

    @JsonCreator
    public ServiceError(@JsonProperty(Fields.errorCode) ErrorCode errorCode, @JsonProperty(Fields.message) String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
