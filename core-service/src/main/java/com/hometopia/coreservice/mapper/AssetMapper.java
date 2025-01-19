package com.hometopia.coreservice.mapper;

import com.hometopia.commons.mapper.ReferenceMapper;
import com.hometopia.coreservice.dto.request.CreateAssetRequest;
import com.hometopia.coreservice.dto.request.UpdateAssetRequest;
import com.hometopia.coreservice.dto.response.CreateAssetResponse;
import com.hometopia.coreservice.dto.response.GetListAssetResponse;
import com.hometopia.coreservice.dto.response.GetOneAssetResponse;
import com.hometopia.coreservice.dto.response.UpdateAssetResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {ReferenceMapper.class, CategoryMapper.class, LocationMapper.class})
public interface AssetMapper {

    Asset toAsset(String assetId);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "locationId", target = "location")
    Asset toAsset(CreateAssetRequest request, @Context User user);

    CreateAssetResponse toCreateAssetResponse(Asset asset);

    GetListAssetResponse toGetListAssetResponse(Asset asset);

    GetOneAssetResponse toGetOneAssetResponse(Asset asset);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "locationId", target = "location")
    Asset updateAsset(@MappingTarget Asset asset, UpdateAssetRequest request);

    UpdateAssetResponse toUpdateAssetResponse(Asset asset);

    @AfterMapping
    default void attachUser(@MappingTarget Asset asset, @Context User user) {
        asset.setUser(user);
    }
}
