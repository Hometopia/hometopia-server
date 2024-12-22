package com.hometopia.commons.message;

import com.hometopia.commons.enumeration.AssetCategory;
import lombok.Builder;

public record Vendor(
        String link,
        String name,
        Address address,
        String website,
        String phoneNumber,
        AssetCategory assetCategory,
        GeoPoint location
) {

    @Builder
    public record Address(
            String line,
            Integer provinceCode,
            String provinceName,
            Integer districtCode,
            String districtName,
            Integer wardCode,
            String wardName
    ) {}

    public record GeoPoint(
            Double lat,
            Double lon
    ) {}

}
