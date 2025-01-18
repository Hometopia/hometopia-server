package com.hometopia.coreservice.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record CostStatisticsByYearResponse(
        List<YearCost> years
) {

    public record YearCost (
            Integer year,
            BigDecimal cost
    ) {}
}
