package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.ProvinceResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;

public interface ProvinceService {
    ListResponse<ProvinceResponse> getListProvinces(CountryCode countryCode);
}
