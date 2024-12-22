package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.ProvinceResponse;
import com.hometopia.commons.enumeration.CountryCode;
import com.hometopia.proto.province.GetProvinceRequest;
import com.hometopia.proto.province.GetProvinceResponse;

public interface ProvinceService {
    ListResponse<ProvinceResponse> getListProvinces(CountryCode countryCode);
    GetProvinceResponse getProvince(GetProvinceRequest request);
}
