package com.hometopia.mediaservice.service;

import com.hometopia.commons.response.MultiStatusResponse;
import com.hometopia.commons.response.RestResponse;
import io.minio.GetObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectStorageService {
    RestResponse<MultiStatusResponse> uploadListFiles(List<MultipartFile> files);
    GetObjectResponse downloadFile(String fileName);
}
