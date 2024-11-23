package com.hometopia.coreservice.mapper;

import com.hometopia.coreservice.dto.response.GetListAssetLifeCycleResponse;
import com.hometopia.coreservice.entity.AssetLifeCycle;
import org.mapstruct.Mapper;

@Mapper
public interface AssetLifeCycleMapper {
    GetListAssetLifeCycleResponse toGetListAssetLifeCycleResponse(AssetLifeCycle assetLifeCycle);
}
