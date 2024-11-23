package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, String>, JpaSpecificationExecutor<Asset> {
    @Query("select a from Asset a where a.user.id = :userId")
    List<Asset> findAllByUserId(String userId);
}
