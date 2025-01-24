package com.hometopia.coreservice.dto.response;

import com.hometopia.commons.enumeration.AssetCategory;

public record SuggestedAssetInformationResponse(
        AssetCategory label,
        Integer usefulLife,
        Integer maintenanceCycle
) {}
