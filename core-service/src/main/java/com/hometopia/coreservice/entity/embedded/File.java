package com.hometopia.coreservice.entity.embedded;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record File(
        @NotBlank
        String originalFileName,
        @NotBlank
        String fileName,
        @NotBlank
        String fileExtension,
        @NotBlank
        String mimeType,
        @NotBlank
        String bucket,
        @NotBlank
        String path
) implements Serializable {}
