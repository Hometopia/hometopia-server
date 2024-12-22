package com.hometopia.coreservice.repository.custom;

import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.commons.enumeration.CountryCode;

public interface DistrictLanRepositoryCustom {
    DistrictLan findByNameAndProvinceIdAndCountryCode(String name, Integer provinceId, CountryCode countryCode);
}
