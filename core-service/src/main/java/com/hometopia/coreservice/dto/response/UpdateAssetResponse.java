package com.hometopia.coreservice.dto.response;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.enumeration.AssetStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

public record UpdateAssetResponse(
        String id,
        Instant createdAt,
        String createdBy,
        String name,
        String description,
        ArrayList<File> images,
        LocalDate purchaseDate,
        String purchasePlace,
        BigDecimal purchasePrice,
        String brand,
        String serialNumber,
        Location location,
        LocalDate warrantyExpiryDate,
        ArrayList<File> documents,
        AssetStatus status,
        AssetCategory label,
        Integer maintenanceCycle,
        Integer usefulLife,
        Category category
) {
    public record Category(
            String id,
            String name,
            Parent parent
    ) {
        public record Parent(
                String id,
                String name
        ) {}
    }

    public record Location(
            String id,
            String name
    ) {}
}