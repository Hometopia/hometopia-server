package com.hometopia.coreservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CostStatisticsByMonthResponse(
        BigDecimal total,
        BigDecimal january,
        BigDecimal february,
        BigDecimal march,
        BigDecimal april,
        BigDecimal may,
        BigDecimal june,
        BigDecimal july,
        BigDecimal august,
        BigDecimal september,
        BigDecimal october,
        BigDecimal november,
        BigDecimal december
) {}
