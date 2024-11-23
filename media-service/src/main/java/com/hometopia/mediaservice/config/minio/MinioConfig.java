package com.hometopia.mediaservice.config.minio;

import com.hometopia.mediaservice.config.property.MinioProperty;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioConfig {

    private final MinioProperty minioProperty;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperty.url())
                .credentials(minioProperty.username(), minioProperty.password())
                .build();
    }
}
