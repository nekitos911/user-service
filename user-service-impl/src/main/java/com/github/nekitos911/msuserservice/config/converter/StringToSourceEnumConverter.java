package com.github.nekitos911.msuserservice.config.converter;

import com.github.nekitos911.msuserservice.enums.Source;
import com.github.nekitos911.msuserservice.exception.HeaderNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToSourceEnumConverter implements Converter<String, Source> {
    @Override
    public Source convert(String source) {
        try {
            return Source.valueOf(source.toUpperCase());
        } catch (Exception e) {
            throw new HeaderNotValidException(source);
        }
    }
}
