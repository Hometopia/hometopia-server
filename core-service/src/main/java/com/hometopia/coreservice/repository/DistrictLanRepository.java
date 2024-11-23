package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.ProvinceLan;
import com.hometopia.coreservice.entity.embedded.DistrictLanId;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictLanRepository extends JpaRepository<DistrictLan, DistrictLanId> {
    Optional<DistrictLan> findOneByIdDistrictIdAndIdCountryCode(Integer districtId, CountryCode countryCode);
    List<DistrictLan> findAllByIdCountryCode(CountryCode code);
}
