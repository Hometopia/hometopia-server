package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.WardResponse;
import com.hometopia.commons.enumeration.CountryCode;
import com.hometopia.proto.ward.GetWardRequest;
import com.hometopia.proto.ward.GetWardResponse;

public interface WardService {
    ListResponse<WardResponse> getListWards(CountryCode countryCode);
    ListResponse<WardResponse> getListWards(Integer districtId, CountryCode countryCode);
    GetWardResponse getWard(GetWardRequest request);
}
