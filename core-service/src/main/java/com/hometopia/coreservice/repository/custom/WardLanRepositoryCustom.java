package com.hometopia.coreservice.repository.custom;

import com.hometopia.coreservice.entity.WardLan;
import com.hometopia.coreservice.entity.enumeration.CountryCode;

import java.util.List;

public interface WardLanRepositoryCustom {
    List<WardLan> findAllByDistrictIdAndCountryCode(Integer districtId, CountryCode code);
    WardLan findByNameAndDistrictIdAndCountryCode(String name, Integer districtId, CountryCode countryCode);
}
