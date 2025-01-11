package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByMonthResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByQuarterResponse;
import com.hometopia.coreservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping(value = "/by-month", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<CostStatisticsByMonthResponse>> getCostStatisticsByMonth(@RequestParam Integer year) {
        return ResponseEntity.ok(statisticsService.getCostStatisticsByMonth(year));
    }

    @GetMapping(value = "/by-quarter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<CostStatisticsByQuarterResponse>> getCostStatisticsByQuarter(@RequestParam Integer year) {
        return ResponseEntity.ok(statisticsService.getCostStatisticsByQuarter(year));
    }
}
