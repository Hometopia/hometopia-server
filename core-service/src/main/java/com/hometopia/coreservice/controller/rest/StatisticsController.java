package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.AssetStatisticsResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByMonthResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByQuarterResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByYearResponse;
import com.hometopia.coreservice.dto.response.OverallStatisticsResponse;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import com.hometopia.coreservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping(value = "/by-month", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<CostStatisticsByMonthResponse>> getCostStatisticsByMonth(
            @RequestParam Integer year,
            @RequestParam ScheduleType type
    ) {
        return ResponseEntity.ok(statisticsService.getCostStatisticsByMonth(year, type));
    }

    @GetMapping(value = "/by-quarter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<CostStatisticsByQuarterResponse>> getCostStatisticsByQuarter(
            @RequestParam Integer year,
            @RequestParam ScheduleType type
    ) {
        return ResponseEntity.ok(statisticsService.getCostStatisticsByQuarter(year, type));
    }

    @GetMapping(value = "/by-year", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<CostStatisticsByYearResponse>> getCostStatisticsByYear(
            @RequestParam List<Integer> years,
            @RequestParam ScheduleType type
    ) {
        return ResponseEntity.ok(statisticsService.getCostStatisticsByYear(years, type));
    }

    @GetMapping(value = "/asset-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<AssetStatisticsResponse>> getAssetStatisticsByYear(
            @RequestParam Integer year
    ) {
        return ResponseEntity.ok(statisticsService.getAssetStatisticsByYear(year));
    }

    @GetMapping(value = "/overall-statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<OverallStatisticsResponse>> getOverallStatistics() {
        return ResponseEntity.ok(statisticsService.getOverallStatistics());
    }
}
