package com.hometopia.coreservice.service;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByMonthResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByQuarterResponse;

public interface StatisticsService {
    RestResponse<CostStatisticsByMonthResponse> getCostStatisticsByMonth(Integer year);
    RestResponse<CostStatisticsByQuarterResponse> getCostStatisticsByQuarter(Integer year);
}
