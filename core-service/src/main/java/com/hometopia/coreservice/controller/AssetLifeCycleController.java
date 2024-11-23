package com.hometopia.coreservice.controller;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.GetListAssetLifeCycleResponse;
import com.hometopia.coreservice.service.AssetLifeCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asset-life-cycles")
@RequiredArgsConstructor
public class AssetLifeCycleController {

    private final AssetLifeCycleService assetLifeCycleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<ListResponse<GetListAssetLifeCycleResponse>>> getListAssetLifeCycle(
            @RequestParam String filter) {
        return ResponseEntity.ok(assetLifeCycleService.getListAssetLifeCycle(filter));
    }
}
