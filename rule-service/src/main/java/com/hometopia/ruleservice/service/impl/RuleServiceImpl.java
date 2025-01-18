package com.hometopia.ruleservice.service.impl;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.ruleservice.mapper.AssetCategoryMapper;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleRequest;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleResponse;
import com.hometopia.proto.asset.GetAssetUsefulLifeRequest;
import com.hometopia.proto.asset.GetAssetUsefulLifeResponse;
import com.hometopia.ruleservice.dto.rule.AssetMaintenanceCycle;
import com.hometopia.ruleservice.dto.rule.AssetUsefulLife;
import com.hometopia.ruleservice.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final AssetCategoryMapper assetCategoryMapper;
    private final KieContainer assetUsefulLifeRuleContainer;
    private final KieContainer assetMaintenanceCycleRuleContainer;

    @Override
    public RestResponse<AssetUsefulLife> getAssetUsefulLife(AssetCategory category) {
        return RestResponse.ok(getUsefulLife(category));
    }

    @Override
    public RestResponse<AssetMaintenanceCycle> getAssetMaintenanceCycle(AssetCategory category) {
        return RestResponse.ok(getMaintenanceCycle(category));
    }

    @Override
    public GetAssetUsefulLifeResponse getAssetUsefulLife(GetAssetUsefulLifeRequest request) {
        return GetAssetUsefulLifeResponse.newBuilder()
                .setUsefulLife(getUsefulLife(assetCategoryMapper.toAssetCategory(request.getCategory())).getUsefulLife())
                .build();
    }

    @Override
    public GetAssetMaintenanceCycleResponse getAssetMaintenanceCycle(GetAssetMaintenanceCycleRequest request) {
        return GetAssetMaintenanceCycleResponse.newBuilder()
                .setMaintenanceCycle(getMaintenanceCycle(assetCategoryMapper.toAssetCategory(request.getCategory())).getMaintenanceCycle())
                .build();
    }

    private AssetUsefulLife getUsefulLife(AssetCategory category) {
        AssetUsefulLife assetUsefulLife = new AssetUsefulLife();
        assetUsefulLife.setCategory(category);
        KieSession kieSession = assetUsefulLifeRuleContainer.newKieSession();
        kieSession.insert(assetUsefulLife);
        kieSession.fireAllRules();
        kieSession.dispose();
        return assetUsefulLife;
    }

    private AssetMaintenanceCycle getMaintenanceCycle(AssetCategory category) {
        AssetMaintenanceCycle assetMaintenanceCycle = new AssetMaintenanceCycle();
        assetMaintenanceCycle.setCategory(category);
        KieSession kieSession = assetMaintenanceCycleRuleContainer.newKieSession();
        kieSession.insert(assetMaintenanceCycle);
        kieSession.fireAllRules();
        kieSession.dispose();
        return assetMaintenanceCycle;
    }
}
