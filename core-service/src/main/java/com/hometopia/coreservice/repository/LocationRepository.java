package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocationRepository extends JpaRepository<Location, String>, JpaSpecificationExecutor<Location> {
}
