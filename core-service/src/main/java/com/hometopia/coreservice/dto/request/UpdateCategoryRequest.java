package com.hometopia.coreservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateCategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        String parentId,
        @NotNull
        List<String> subCategoryIds
) {}
