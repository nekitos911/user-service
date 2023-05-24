package com.github.nekitos911.msuserservice.config;

import com.github.nekitos911.msuserservice.config.converter.StringToSourceEnumConverter;
//import com.github.nekitos911.msuserservice.validator.ValidatorInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSourceEnumConverter());
    }

}
