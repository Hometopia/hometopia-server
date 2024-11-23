package com.hometopia.commons.mapper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.datasource.url")
    public ReferenceMapper referenceMapper() {
        return new ReferenceMapper();
    }
}
