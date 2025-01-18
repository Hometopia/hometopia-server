package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.AssetStatisticsResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByMonthResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByQuarterResponse;
import com.hometopia.coreservice.dto.response.CostStatisticsByYearResponse;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import com.hometopia.coreservice.repository.ScheduleRepository;
import com.hometopia.coreservice.service.StatisticsService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public RestResponse<CostStatisticsByMonthResponse> getCostStatisticsByMonth(Integer year, ScheduleType type) {
        CostStatisticsByMonthResponse.CostStatisticsByMonthResponseBuilder builder = CostStatisticsByMonthResponse.builder();
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        scheduleRepository.getCostStatisticsByMonth(year, type).forEach(rs -> {
            BigDecimal cost = rs.get(1, BigDecimal.class);
            total.set(total.get().add(cost));
            switch (rs.get(0, Integer.class)) {
                case 1:
                    builder.january(cost);
                    break;
                case 2:
                    builder.february(cost);
                    break;
                case 3:
                    builder.march(cost);
                    break;
                case 4:
                    builder.april(cost);
                    break;
                case 5:
                    builder.may(cost);
                    break;
                case 6:
                    builder.june(cost);
                    break;
                case 7:
                    builder.july(cost);
                    break;
                case 8:
                    builder.august(cost);
                    break;
                case 9:
                    builder.september(cost);
                    break;
                case 10:
                    builder.october(cost);
                    break;
                case 11:
                    builder.november(cost);
                    break;
                case 12:
                    builder.december(cost);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid month");
            }
        });
        builder.total(total.get());

        return RestResponse.ok(builder.build());
    }

    @Override
    public RestResponse<CostStatisticsByQuarterResponse> getCostStatisticsByQuarter(Integer year, ScheduleType type) {
        CostStatisticsByQuarterResponse.CostStatisticsByQuarterResponseBuilder builder = CostStatisticsByQuarterResponse.builder();
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);
        scheduleRepository.getCostStatisticsByQuarter(year, type).forEach(rs -> {
            BigDecimal cost = rs.get(1, BigDecimal.class);
            total.set(total.get().add(cost));
            switch (rs.get(0, String.class)) {
                case "Q1":
                    builder.firstQuarter(cost);
                    break;
                case "Q2":
                    builder.secondQuarter(cost);
                    break;
                case "Q3":
                    builder.thirdQuarter(cost);
                    break;
                case "Q4":
                    builder.fourthQuarter(cost);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid quarter");
            }
        });
        builder.total(total.get());

        return RestResponse.ok(builder.build());
    }

    @Override
    public RestResponse<CostStatisticsByYearResponse> getCostStatisticsByYear(List<Integer> years, ScheduleType type) {
        List<CostStatisticsByYearResponse.YearCost> costs = scheduleRepository.getCostStatisticsByYear(years, type).stream()
                .map(rs -> new CostStatisticsByYearResponse.YearCost(rs.get(0, Integer.class), rs.get(1, BigDecimal.class)))
                .toList();

        return RestResponse.ok(new CostStatisticsByYearResponse(costs));
    }

    @Override
    public RestResponse<AssetStatisticsResponse> getAssetStatisticsByYear(Integer year) {
        AssetStatisticsResponse.AssetStatisticsResponseBuilder builder = AssetStatisticsResponse.builder();
        List<Tuple> results = scheduleRepository.getRepairScheduleStatisticsOfAssetByYear(year);

        builder.mostDurable(new AssetStatisticsResponse.AssetRepairCount(
                results.get(0).get(0, String.class),
                results.get(0).get(1, Long.class)
        ));
        builder.mostBroken(new AssetStatisticsResponse.AssetRepairCount(
                results.get(results.size() - 1).get(0, String.class),
                results.get(results.size() - 1).get(1, Long.class)
        ));
        builder.repairCounts(results.stream().map(rs -> new AssetStatisticsResponse.AssetRepairCount(
                        rs.get(0, String.class),
                        rs.get(1, Long.class)))
                .toList()
        );

        results = scheduleRepository.getCostStatisticsOfAssetByYear(year, ScheduleType.REPAIR);
        builder.repairCosts(results.stream().map(rs -> new AssetStatisticsResponse.AssetCost(
                        rs.get(0, String.class),
                        rs.get(1, BigDecimal.class)))
                .toList()
        );

        results = scheduleRepository.getCostStatisticsOfAssetByYear(year, ScheduleType.MAINTENANCE);
        builder.maintenanceCosts(results.stream().map(rs -> new AssetStatisticsResponse.AssetCost(
                        rs.get(0, String.class),
                        rs.get(1, BigDecimal.class)))
                .toList()
        );

        return RestResponse.ok(builder.build());
    }
}
