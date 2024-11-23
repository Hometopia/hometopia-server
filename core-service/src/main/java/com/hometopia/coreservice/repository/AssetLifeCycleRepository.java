package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.AssetLifeCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AssetLifeCycleRepository extends JpaRepository<AssetLifeCycle, String>, JpaSpecificationExecutor<AssetLifeCycle> {
}
