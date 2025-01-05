package com.hometopia.ruleservice.service.impl;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.RestResponse;
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

    private final KieContainer assetUsefulLifeRuleContainer;
    private final KieContainer assetMaintenanceCycleRuleContainer;

    @Override
    public RestResponse<AssetUsefulLife> getAssetUsefulLife(AssetCategory category) {
        AssetUsefulLife assetUsefulLife = new AssetUsefulLife();
        assetUsefulLife.setCategory(category);
        KieSession kieSession = assetUsefulLifeRuleContainer.newKieSession();
        kieSession.insert(assetUsefulLife);
        kieSession.fireAllRules();
        kieSession.dispose();
        return RestResponse.ok(assetUsefulLife);
    }

    @Override
    public RestResponse<AssetMaintenanceCycle> getAssetMaintenanceCycle(AssetCategory category) {
        AssetMaintenanceCycle assetMaintenanceCycle = new AssetMaintenanceCycle();
        assetMaintenanceCycle.setCategory(category);
        KieSession kieSession = assetMaintenanceCycleRuleContainer.newKieSession();
        kieSession.insert(assetMaintenanceCycle);
        kieSession.fireAllRules();
        kieSession.dispose();
        return RestResponse.ok(assetMaintenanceCycle);
    }
}
