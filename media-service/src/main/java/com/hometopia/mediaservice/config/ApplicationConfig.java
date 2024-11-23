package com.hometopia.mediaservice.config;

import com.hometopia.commons.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({SecurityConfig.class})
@Configuration
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class ApplicationConfig {}
