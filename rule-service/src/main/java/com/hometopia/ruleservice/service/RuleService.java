package com.hometopia.ruleservice.service;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.ruleservice.dto.rule.AssetUsefulLife;

public interface RuleService {
    RestResponse<AssetUsefulLife> getAssetUsefulLife(AssetCategory category);
}
