package com.hometopia.mediaservice.controller;

import com.hometopia.commons.response.MultiStatusResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.mediaservice.exception.ObjectStorageException;
import com.hometopia.mediaservice.service.ObjectStorageService;
import com.hometopia.mediaservice.utils.FileUtils;
import io.minio.GetObjectResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final ObjectStorageService objectStorageService;

    @GetMapping
    public ResponseEntity<byte[]> getFile(@RequestParam String fileName) {
        try (GetObjectResponse response = objectStorageService.downloadFile(fileName)) {
            byte[] fileContent = response.readAllBytes();
            return ResponseEntity.ok()
                    .contentType(FileUtils.getMediaType(FilenameUtils.getExtension(fileName)))
                    .body(fileContent);
        } catch (IOException e) {
            throw new ObjectStorageException(e.getMessage());
        }
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<MultiStatusResponse>> uploadOneFile(@RequestParam List<MultipartFile> files) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(objectStorageService.uploadListFiles(files));
    }
}
