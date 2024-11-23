package com.hometopia.coreservice.dto.request;

import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.enumeration.AssetStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public record CreateAssetRequest(
        @NotBlank
        String name,
        String description,
        ArrayList<File> images,
        @NotNull
        LocalDate purchaseDate,
        @NotBlank
        String purchasePlace,
        @NotNull
        @Positive
        BigDecimal purchasePrice,
        @NotBlank
        String vendor,
        @NotBlank
        String serialNumber,
        @NotBlank
        String location,
        @NotNull
        @FutureOrPresent
        LocalDate warrantyExpiryDate,
        @NotNull
        ArrayList<File> documents,
        @NotNull
        AssetStatus status,
        @Positive
        Integer maintenanceCycle,
        String categoryId
) {}