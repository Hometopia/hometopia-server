package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.GetListAssetLifeCycleResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.Location;
import com.hometopia.coreservice.entity.Schedule;

public interface AssetLifeCycleService {
    RestResponse<ListResponse<GetListAssetLifeCycleResponse>> getListAssetLifeCycle(String filter);
    void createAssetLifeCycleByStatus(Asset asset);
    void createAssetLifeCycleByLocation(Asset asset, Location previousLocation);
    void createAssetLifeCycleBySchedule(Schedule schedule);
}
