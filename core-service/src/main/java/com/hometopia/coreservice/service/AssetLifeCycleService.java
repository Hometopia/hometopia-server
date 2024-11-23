package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.GetListAssetLifeCycleResponse;
import com.hometopia.coreservice.entity.Asset;

public interface AssetLifeCycleService {
    RestResponse<ListResponse<GetListAssetLifeCycleResponse>> getListAssetLifeCycle(String filter);
    void createAssetLifeCycle(Asset asset);
}
