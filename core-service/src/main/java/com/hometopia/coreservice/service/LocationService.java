package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateLocationRequest;
import com.hometopia.coreservice.dto.request.UpdateLocationRequest;
import com.hometopia.coreservice.dto.response.CreateLocationResponse;
import com.hometopia.coreservice.dto.response.GetListLocationResponse;
import com.hometopia.coreservice.dto.response.GetOneLocationResponse;
import com.hometopia.coreservice.dto.response.UpdateLocationResponse;

import java.util.List;

public interface LocationService {
    RestResponse<ListResponse<GetListLocationResponse>> getListLocations(int page, int size, String sort, String filter, boolean all);
    RestResponse<GetOneLocationResponse> getOneLocation(String id);
    RestResponse<CreateLocationResponse> createLocation(CreateLocationRequest request);
    RestResponse<UpdateLocationResponse> updateLocation(String id, UpdateLocationRequest request);
    void deleteLocation(String id);
    void deleteListLocations(List<String> ids);
}
