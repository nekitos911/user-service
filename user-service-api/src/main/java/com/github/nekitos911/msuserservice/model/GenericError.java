package com.github.nekitos911.msuserservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Getter
public class GenericError {
//    private final UUID requestId;
    private final ServiceError description;

    @JsonCreator
    public GenericError(@JsonProperty(Fields.description) ServiceError description) {
        this.description = description;
    }
}
