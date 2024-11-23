package com.hometopia.ruleservice.config;

import com.hometopia.commons.mapper.MapperConfig;
import com.hometopia.commons.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({SecurityConfig.class, MapperConfig.class})
@Configuration
public class ApplicationConfig {
}
