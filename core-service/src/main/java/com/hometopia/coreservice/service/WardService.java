package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.WardResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;

public interface WardService {
    ListResponse<WardResponse> getListWards(CountryCode countryCode);
    ListResponse<WardResponse> getListWards(Integer districtId, CountryCode countryCode);
}
