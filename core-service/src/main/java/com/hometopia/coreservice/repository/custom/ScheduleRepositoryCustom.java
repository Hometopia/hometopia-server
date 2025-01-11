package com.hometopia.coreservice.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ScheduleRepositoryCustom {
    List<Tuple> getCostStatisticsByMonth(Integer year);
    List<Tuple> getCostStatisticsByQuarter(Integer year);
}
