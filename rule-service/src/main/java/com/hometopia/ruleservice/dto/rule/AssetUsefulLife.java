package com.hometopia.ruleservice.dto.rule;

import com.hometopia.commons.enumeration.AssetCategory;
import lombok.Data;

@Data
public class AssetUsefulLife {
    private AssetCategory category;
    private Integer usefulLife;
}
