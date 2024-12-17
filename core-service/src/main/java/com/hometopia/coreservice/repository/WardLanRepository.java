package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.WardLan;
import com.hometopia.coreservice.entity.embedded.WardLanId;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.repository.custom.WardLanRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface WardLanRepository extends JpaRepository<WardLan, WardLanId>, QuerydslPredicateExecutor<WardLan>, WardLanRepositoryCustom {
    Optional<WardLan> findOneByIdWardIdAndIdCountryCode(Integer wardId, CountryCode countryCode);
    Optional<WardLan> findOneByNameContainingIgnoreCaseAndIdCountryCode(String name, CountryCode countryCode);
    List<WardLan> findAllByIdCountryCode(CountryCode code);
}
