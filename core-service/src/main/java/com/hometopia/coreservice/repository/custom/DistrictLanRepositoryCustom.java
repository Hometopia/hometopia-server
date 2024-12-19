package com.hometopia.coreservice.repository.custom;

import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.enumeration.CountryCode;

public interface DistrictLanRepositoryCustom {
    DistrictLan findByNameAndProvinceIdAndCountryCode(String name, Integer provinceId, CountryCode countryCode);
}
