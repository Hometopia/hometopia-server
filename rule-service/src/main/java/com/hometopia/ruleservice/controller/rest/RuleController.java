package com.hometopia.ruleservice.controller.rest;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.ruleservice.dto.rule.AssetUsefulLife;
import com.hometopia.ruleservice.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;

    @GetMapping("/useful-life")
    public ResponseEntity<RestResponse<AssetUsefulLife>> getAssetUsefulLife(@RequestParam AssetCategory category) {
        return ResponseEntity.ok(ruleService.getAssetUsefulLife(category));
    }
}
