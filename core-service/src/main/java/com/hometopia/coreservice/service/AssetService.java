package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateAssetRequest;
import com.hometopia.coreservice.dto.request.UpdateAssetRequest;
import com.hometopia.coreservice.dto.response.CreateAssetResponse;
import com.hometopia.coreservice.dto.response.GetAssetDepreciationResponse;
import com.hometopia.coreservice.dto.response.GetListAssetResponse;
import com.hometopia.coreservice.dto.response.GetOneAssetResponse;
import com.hometopia.coreservice.dto.response.UpdateAssetResponse;
import com.hometopia.coreservice.entity.Asset;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AssetService {
    RestResponse<ListResponse<GetListAssetResponse>> getListAssets(int page, int size, String sort, String filter, boolean all);
    RestResponse<GetOneAssetResponse> getOneAsset(String id);
    RestResponse<CreateAssetResponse> createAsset(CreateAssetRequest request);
    RestResponse<UpdateAssetResponse> updateAsset(String id, UpdateAssetRequest request);
    void deleteAsset(String id);
    void deleteListAssets(List<String> ids);
    RestResponse<GetAssetDepreciationResponse> getAssetDepreciation(String id);
    Map<Asset, BigDecimal> getAssetsCurrentValue(String userId);
}
