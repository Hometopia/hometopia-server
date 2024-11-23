package com.hometopia.mediaservice.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("minio")
public record MinioProperty(
        String url,
        String username,
        String password,
        String baseBucket
) {}
