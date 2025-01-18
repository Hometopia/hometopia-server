package com.hometopia.coreservice.client;

import com.hometopia.coreservice.client.dto.request.PredictAssetCategoryRequest;
import com.hometopia.coreservice.client.dto.response.PredictAssetCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ClassificationServiceClient", url = "http://193.203.161.141:5000")
public interface ClassificationServiceClient {

    @PostMapping(path = "/predict", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PredictAssetCategoryResponse> predictAssetCategory(@RequestBody PredictAssetCategoryRequest request);
}
