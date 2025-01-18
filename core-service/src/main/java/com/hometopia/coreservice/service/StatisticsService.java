package com.hometopia.coreservice.service;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.AssetStatisticsResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByMonthResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByQuarterResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByYearResponse;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;

import java.util.List;

public interface StatisticsService {
    RestResponse<CostStatisticsByMonthResponse> getCostStatisticsByMonth(Integer year, ScheduleType type);
    RestResponse<CostStatisticsByQuarterResponse> getCostStatisticsByQuarter(Integer year, ScheduleType type);
    RestResponse<CostStatisticsByYearResponse> getCostStatisticsByYear(List<Integer> years, ScheduleType type);
    RestResponse<AssetStatisticsResponse> getAssetStatisticsByYear(Integer year);
}
