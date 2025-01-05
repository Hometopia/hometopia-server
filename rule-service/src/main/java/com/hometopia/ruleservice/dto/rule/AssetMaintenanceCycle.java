package com.hometopia.ruleservice.dto.rule;

import com.hometopia.commons.enumeration.AssetCategory;
import lombok.Data;

@Data
public class AssetMaintenanceCycle {
    private AssetCategory category;
    private Integer maintenanceCycle;
}
