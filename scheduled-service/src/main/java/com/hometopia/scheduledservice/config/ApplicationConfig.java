package com.hometopia.scheduledservice.config;

import com.hometopia.commons.mapper.MapperConfig;
import com.hometopia.commons.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Import({SecurityConfig.class, MapperConfig.class})
public class ApplicationConfig {
}
