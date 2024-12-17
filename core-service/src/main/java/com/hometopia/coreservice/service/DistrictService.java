package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.DistrictResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.proto.district.GetDistrictRequest;
import com.hometopia.proto.district.GetDistrictResponse;

import java.util.Optional;

public interface DistrictService {
    ListResponse<DistrictResponse> getListDistricts(CountryCode code);
    ListResponse<DistrictResponse> getListDistricts(Integer provinceId, CountryCode code);
    GetDistrictResponse getGetDistrict(GetDistrictRequest request);
}
