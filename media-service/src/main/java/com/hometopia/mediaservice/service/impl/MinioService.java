package com.hometopia.mediaservice.service.impl;

import com.hometopia.commons.response.MultiStatusResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.mediaservice.config.property.MinioProperty;
import com.hometopia.mediaservice.dto.response.UploadFileResponse;
import com.hometopia.mediaservice.exception.ObjectStorageException;
import com.hometopia.mediaservice.service.ObjectStorageService;
import com.hometopia.mediaservice.utils.FileUtils;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MinioService implements ObjectStorageService {

    private final MinioClient minioClient;
    private final MinioProperty minioProperty;

    @Override
    public RestResponse<MultiStatusResponse> uploadListFiles(List<MultipartFile> files) {
        List<MultiStatusResponse.Item> items = new ArrayList<>(files.size());

        files.forEach(file -> {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = FileUtils.getFilename(file.getOriginalFilename());
            String mimeType = FileUtils.getMineType(fileExtension);

            try {
                ObjectWriteResponse response = minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioProperty.baseBucket())
                                .object(fileName)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(mimeType)
                                .build()
                );

                items.add(MultiStatusResponse.Success.ok(UploadFileResponse.builder()
                        .originalFileName(file.getOriginalFilename())
                        .fileName(response.object())
                        .fileExtension(fileExtension)
                        .mimeType(mimeType)
                        .bucket(response.bucket())
                        .path("/%s/%s".formatted(response.bucket(), response.object()))
                        .build()));
            } catch (Exception e) {
                String message = "File [[%s]] can not be uploaded because of: %s"
                        .formatted(file.getOriginalFilename(), e.getMessage());
                items.add(MultiStatusResponse.Failure.internalServerError(message));
            }
        });

        return RestResponse.multiStatus(MultiStatusResponse.of(items));
    }

    @Override
    public GetObjectResponse downloadFile(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioProperty.baseBucket())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            throw new ObjectStorageException(e.getMessage());
        }
    }
}
