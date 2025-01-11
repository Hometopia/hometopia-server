package com.hometopia.coreservice.repository.custom.impl;

import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.entity.QSchedule;
import com.hometopia.coreservice.repository.AssetRepository;
import com.hometopia.coreservice.repository.custom.ScheduleRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final AssetRepository assetRepository;

    @Override
    public List<Tuple> getCostStatisticsByMonth(Integer year) {
        return queryFactory.select(
                        QSchedule.schedule.start.month(),
                        QSchedule.schedule.cost.sum())
                .from(QSchedule.schedule)
                .groupBy(QSchedule.schedule.start.month())
                .where(QSchedule.schedule.asset.id.in(assetRepository.findAllIdsByUserId(SecurityUtils.getCurrentUserId()))
                        .and(QSchedule.schedule.cost.isNotNull())
                        .and(QSchedule.schedule.start.year().eq(year)))
                .orderBy(QSchedule.schedule.start.month().asc())
                .fetch();
    }

    @Override
    public List<Tuple> getCostStatisticsByQuarter(Integer year) {
        return queryFactory.select(
                        Expressions.stringTemplate(
                                """
                                            CASE
                                            WHEN EXTRACT(MONTH FROM {0}) IN (1, 2, 3) THEN 'Q1'
                                            WHEN EXTRACT(MONTH FROM {0}) IN (4, 5, 6) THEN 'Q2'
                                            WHEN EXTRACT(MONTH FROM {0}) IN (7, 8, 9) THEN 'Q3'
                                            WHEN EXTRACT(MONTH FROM {0}) IN (10, 11, 12) THEN 'Q4'
                                            END
                                        """,
                                QSchedule.schedule.start
                        ),
                        QSchedule.schedule.cost.sum()
                )
                .from(QSchedule.schedule)
                .where(QSchedule.schedule.asset.id.in(assetRepository.findAllIdsByUserId(SecurityUtils.getCurrentUserId()))
                        .and(QSchedule.schedule.cost.isNotNull())
                        .and(QSchedule.schedule.start.year().eq(year)))
                .groupBy(Expressions.stringTemplate(
                        """
                                    CASE
                                    WHEN EXTRACT(MONTH FROM {0}) IN (1, 2, 3) THEN 'Q1'
                                    WHEN EXTRACT(MONTH FROM {0}) IN (4, 5, 6) THEN 'Q2'
                                    WHEN EXTRACT(MONTH FROM {0}) IN (7, 8, 9) THEN 'Q3'
                                    WHEN EXTRACT(MONTH FROM {0}) IN (10, 11, 12) THEN 'Q4'
                                    END
                                 """,
                        QSchedule.schedule.start
                ))
                .fetch();
    }
}
