package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, String>, JpaSpecificationExecutor<Asset> {
    @Query("select a.id from Asset a where a.user.id = :userId")
    List<Integer> findAllIdsByUserId(String userId);

    @Query(value = """
            SELECT *
            FROM core_service.public.asset a
            WHERE status = 'IN_USE' AND maintenance_cycle is not null AND (
                    EXTRACT(YEAR FROM AGE(CURRENT_DATE, purchase_date)) * 12 +
                    EXTRACT(MONTH FROM AGE(CURRENT_DATE, purchase_date))
            ) % maintenance_cycle = 0;
    """, nativeQuery = true)
    List<Asset> findAllInMaintenanceInterval();
}
