package com.hometopia.coreservice.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record GetAssetDepreciationResponse(
        List<Depreciation> straightLineDepreciation,
        List<Depreciation> decliningBalanceDepreciation
) {

    public record Depreciation(
            Integer year,
            BigDecimal value
    ) {}
}
