package com.github.nekitos911.msuserservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@Getter
@FieldNameConstants
public class GenericResponse<Result> {
    private final GenericError error;
    private final Result data;

    public static <T>GenericResponse<T> ofResult(T result) {
        return new GenericResponse<>(null, result);
    }

    public static <T>GenericResponse<T> ofError(GenericError error) {
        return new GenericResponse<>(error, null);
    }

    @JsonCreator
    public GenericResponse(@JsonProperty(Fields.error) GenericError error, @JsonProperty(Fields.data) Result data) {
        this.error = error;
        this.data = data;

        if (error == null && data == null) {
            throw new RuntimeException();
        }

        if (error != null && data != null) {
            throw new RuntimeException();
        }
    }
}
