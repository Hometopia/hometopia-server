package com.hometopia.commons.message;

import com.hometopia.commons.enumeration.AssetCategory;

public record Vendor(
        String link,
        String name,
        String address,
        String website,
        String phoneNumber,
        AssetCategory assetCategory
) {}
