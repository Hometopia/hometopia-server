package com.hometopia.vendorservice.mapper;

import com.hometopia.commons.enumeration.AssetCategory;
import org.mapstruct.Mapper;

@Mapper
public interface AssetCategoryMapper {

    default AssetCategory toAssetCategory(com.hometopia.proto.asset.AssetCategory assetCategory) {
        return switch (assetCategory) {
            case BED -> AssetCategory.BED;
            case CAR -> AssetCategory.CAR;
            case CHAIR -> AssetCategory.CHAIR;
            case LAMP -> AssetCategory.LAMP;
            case LAPTOP -> AssetCategory.LAPTOP;
            case MOBILE_PHONE -> AssetCategory.MOBILE_PHONE;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unrecognized AssetCategory");
        };
    }
}
