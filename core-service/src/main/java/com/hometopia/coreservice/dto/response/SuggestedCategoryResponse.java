package com.hometopia.coreservice.dto.response;

import java.util.List;

public record SuggestedCategoryResponse(
        String name,
        List<SuggestedCategoryResponse> subCategories
) {}
