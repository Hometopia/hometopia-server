package com.hometopia.coreservice.repository.custom;

import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Tuple> getCostStatisticsByMonth(Integer year, ScheduleType type);
    List<Tuple> getCostStatisticsByQuarter(Integer year, ScheduleType type);
    List<Tuple> getCostStatisticsByYear(List<Integer> years, ScheduleType type);
    List<Tuple> getRepairScheduleStatisticsOfAssetByYear(Integer year);
    List<Tuple> getCostStatisticsOfAssetByYear(Integer year, ScheduleType type);
}
