package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.ProvinceLan;
import com.hometopia.coreservice.entity.embedded.ProvinceLanId;
import com.hometopia.commons.enumeration.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinceLanRepository extends JpaRepository<ProvinceLan, ProvinceLanId> {
    Optional<ProvinceLan> findOneByIdProvinceIdAndIdCountryCode(Integer provinceId, CountryCode countryCode);
    Optional<ProvinceLan> findOneByNameContainingIgnoreCaseAndIdCountryCode(String name, CountryCode countryCode);
    List<ProvinceLan> findAllByIdCountryCode(CountryCode code);
}
