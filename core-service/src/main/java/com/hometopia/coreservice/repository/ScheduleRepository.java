package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.Schedule;
import com.hometopia.coreservice.repository.custom.ScheduleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, String>, JpaSpecificationExecutor<Schedule>, ScheduleRepositoryCustom {
    List<Schedule> findByStartBetween(LocalDateTime start, LocalDateTime end);
}
