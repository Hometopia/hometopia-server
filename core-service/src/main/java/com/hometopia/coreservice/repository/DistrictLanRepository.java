package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.embedded.DistrictLanId;
import com.hometopia.commons.enumeration.CountryCode;
import com.hometopia.coreservice.repository.custom.DistrictLanRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictLanRepository extends JpaRepository<DistrictLan, DistrictLanId>, DistrictLanRepositoryCustom {
    Optional<DistrictLan> findOneByIdDistrictIdAndIdCountryCode(Integer districtId, CountryCode countryCode);
    List<DistrictLan> findAllByIdCountryCode(CountryCode code);
}
