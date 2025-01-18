package com.hometopia.coreservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record AssetStatisticsResponse(
        AssetRepairCount mostBroken,
        AssetRepairCount mostDurable,
        List<AssetRepairCount> repairCounts,
        List<AssetCost> maintenanceCosts,
        List<AssetCost> repairCosts
) {

    public record AssetCost(
            String name,
            BigDecimal cost
    ) {}

    public record AssetRepairCount(
            String name,
            Long count
    ) {}
}
