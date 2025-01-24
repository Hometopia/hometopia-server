package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.AssetLifeCycle;
import com.hometopia.coreservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AssetLifeCycleRepository extends JpaRepository<AssetLifeCycle, String>, JpaSpecificationExecutor<AssetLifeCycle> {
    Optional<AssetLifeCycle> findBySchedule(Schedule schedule);
}
