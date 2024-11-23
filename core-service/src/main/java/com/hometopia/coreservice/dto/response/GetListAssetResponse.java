package com.hometopia.coreservice.dto.response;

import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.enumeration.AssetStatus;

import java.util.ArrayList;

public record GetListAssetResponse(
        String id,
        String name,
        String description,
        ArrayList<File> images,
        AssetStatus status,
        Category category
) {
    public record Category(
            String id,
            String name,
            CreateAssetResponse.Category.Parent parent
    ) {
        public record Parent(
                String id,
                String name
        ) {}
    }
}
