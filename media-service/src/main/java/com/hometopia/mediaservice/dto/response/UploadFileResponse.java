package com.hometopia.mediaservice.dto.response;

import lombok.Builder;

@Builder
public record UploadFileResponse(
        String originalFileName,
        String fileName,
        String fileExtension,
        String mimeType,
        String bucket,
        String path
) {}
