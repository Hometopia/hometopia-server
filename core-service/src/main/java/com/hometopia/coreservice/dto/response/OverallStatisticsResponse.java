package com.hometopia.coreservice.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record OverallStatisticsResponse(
        Long totalAssets,
        Long totalCategories,
        BigDecimal totalValueOfAllAssets,
        List<AssetValue> assetValues
) {

    public record AssetValue(
            String name,
            BigDecimal value
    ) {}
}
