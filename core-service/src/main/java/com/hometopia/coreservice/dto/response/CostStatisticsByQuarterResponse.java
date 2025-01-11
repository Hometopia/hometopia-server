package com.hometopia.coreservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CostStatisticsByQuarterResponse(
        BigDecimal firstQuarter,
        BigDecimal secondQuarter,
        BigDecimal thirdQuarter,
        BigDecimal fourthQuarter
) {}
