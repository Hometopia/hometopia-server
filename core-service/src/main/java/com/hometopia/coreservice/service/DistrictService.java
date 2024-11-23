package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.DistrictResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;

public interface DistrictService {
    ListResponse<DistrictResponse> getListDistricts(CountryCode code);
    ListResponse<DistrictResponse> getListDistricts(Integer provinceId, CountryCode code);
}
