package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByMonthResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByQuarterResponse;
import com.hometopia.coreservice.repository.ScheduleRepository;
import com.hometopia.coreservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public RestResponse<CostStatisticsByMonthResponse> getCostStatisticsByMonth(Integer year) {
        CostStatisticsByMonthResponse.CostStatisticsByMonthResponseBuilder builder = CostStatisticsByMonthResponse.builder();
        scheduleRepository.getCostStatisticsByMonth(year).forEach(rs -> {
            switch (rs.get(0, Integer.class)) {
                case 1:
                    builder.january(rs.get(1, BigDecimal.class));
                    break;
                case 2:
                    builder.february(rs.get(1, BigDecimal.class));
                    break;
                case 3:
                    builder.march(rs.get(1, BigDecimal.class));
                    break;
                case 4:
                    builder.april(rs.get(1, BigDecimal.class));
                    break;
                case 5:
                    builder.may(rs.get(1, BigDecimal.class));
                    break;
                case 6:
                    builder.june(rs.get(1, BigDecimal.class));
                    break;
                case 7:
                    builder.july(rs.get(1, BigDecimal.class));
                    break;
                case 8:
                    builder.august(rs.get(1, BigDecimal.class));
                    break;
                case 9:
                    builder.september(rs.get(1, BigDecimal.class));
                    break;
                case 10:
                    builder.october(rs.get(1, BigDecimal.class));
                    break;
                case 11:
                    builder.november(rs.get(1, BigDecimal.class));
                    break;
                case 12:
                    builder.december(rs.get(1, BigDecimal.class));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid month");
            }
        });
        return RestResponse.ok(builder.build());
    }

    @Override
    public RestResponse<CostStatisticsByQuarterResponse> getCostStatisticsByQuarter(Integer year) {
        CostStatisticsByQuarterResponse.CostStatisticsByQuarterResponseBuilder builder = CostStatisticsByQuarterResponse.builder();
        scheduleRepository.getCostStatisticsByQuarter(year).forEach(rs -> {
            switch (rs.get(0, String.class)) {
                case "Q1":
                    builder.firstQuarter(rs.get(1, BigDecimal.class));
                    break;
                case "Q2":
                    builder.secondQuarter(rs.get(1, BigDecimal.class));
                    break;
                case "Q3":
                    builder.thirdQuarter(rs.get(1, BigDecimal.class));
                    break;
                case "Q4":
                    builder.fourthQuarter(rs.get(1, BigDecimal.class));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid quarter");
            }
        });
        return RestResponse.ok(builder.build());
    }
}
