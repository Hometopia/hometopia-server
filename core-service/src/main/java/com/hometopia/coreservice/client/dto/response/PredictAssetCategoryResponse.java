package com.hometopia.coreservice.client.dto.response;

import com.hometopia.commons.enumeration.AssetCategory;

public record PredictAssetCategoryResponse(
        AssetCategory prediction
) {}
