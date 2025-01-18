package com.hometopia.ruleservice.service;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleRequest;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleResponse;
import com.hometopia.proto.asset.GetAssetUsefulLifeRequest;
import com.hometopia.proto.asset.GetAssetUsefulLifeResponse;
import com.hometopia.ruleservice.dto.rule.AssetMaintenanceCycle;
import com.hometopia.ruleservice.dto.rule.AssetUsefulLife;

public interface RuleService {
    RestResponse<AssetUsefulLife> getAssetUsefulLife(AssetCategory category);
    RestResponse<AssetMaintenanceCycle> getAssetMaintenanceCycle(AssetCategory category);
    GetAssetUsefulLifeResponse getAssetUsefulLife(GetAssetUsefulLifeRequest request);
    GetAssetMaintenanceCycleResponse getAssetMaintenanceCycle(GetAssetMaintenanceCycleRequest request);
}
