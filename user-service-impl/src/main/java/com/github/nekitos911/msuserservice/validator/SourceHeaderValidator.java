package com.github.nekitos911.msuserservice.validator;

import com.github.nekitos911.msuserservice.dto.UserDto;
import com.github.nekitos911.msuserservice.enums.Source;

public interface SourceHeaderValidator {

    void validate(Source source, UserDto userDto);
}
