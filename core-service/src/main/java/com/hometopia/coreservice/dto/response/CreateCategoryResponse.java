package com.hometopia.coreservice.dto.response;

import java.util.List;

public record CreateCategoryResponse(
        String id,
        String name,
        String description,
        List<CreateCategoryResponse> subCategories
) {}
