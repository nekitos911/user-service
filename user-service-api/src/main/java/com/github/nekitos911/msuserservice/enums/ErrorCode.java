package com.github.nekitos911.msuserservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    GENERAL_ERROR("US_001", true),
    MISSING_REQUIRED_HEADER("US_002", true),
    USER_NOT_FOUND("US_003", true),
    SOURCE_HEADER_VALIDATION_ERROR("US_004", true),
    SOURCE_HEADER_NOT_VALID("US_005", true),
    EMPTY_SEARCH_PARAMS("US_006", true),
    FIELDS_VALIDATION_ERROR("US_007", true)
    ;

    private final String code;
    private final Boolean isCritical;
}
