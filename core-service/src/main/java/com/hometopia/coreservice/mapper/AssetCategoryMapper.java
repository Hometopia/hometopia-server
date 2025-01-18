package com.hometopia.coreservice.mapper;

import com.hometopia.proto.asset.AssetCategory;
import org.mapstruct.Mapper;

@Mapper
public interface AssetCategoryMapper {

    default AssetCategory toAssetCategoryProto(com.hometopia.commons.enumeration.AssetCategory assetCategory) {
        return switch (assetCategory) {
            case BED -> AssetCategory.BED;
            case CAR -> AssetCategory.CAR;
            case CHAIR -> AssetCategory.CHAIR;
            case LAMP -> AssetCategory.LAMP;
            case LAPTOP -> AssetCategory.LAPTOP;
            case MOBILE_PHONE -> AssetCategory.MOBILE_PHONE;
            default -> throw new IllegalArgumentException("Invalid AssetCategory: " + assetCategory);
        };
    }

    default com.hometopia.commons.enumeration.AssetCategory toAssetCategory(AssetCategory assetCategory) {
        return switch (assetCategory) {
            case BED -> com.hometopia.commons.enumeration.AssetCategory.BED;
            case CAR -> com.hometopia.commons.enumeration.AssetCategory.CAR;
            case CHAIR -> com.hometopia.commons.enumeration.AssetCategory.CHAIR;
            case LAMP -> com.hometopia.commons.enumeration.AssetCategory.LAMP;
            case LAPTOP -> com.hometopia.commons.enumeration.AssetCategory.LAPTOP;
            case MOBILE_PHONE -> com.hometopia.commons.enumeration.AssetCategory.MOBILE_PHONE;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Unrecognized AssetCategory");
        };
    }
}
