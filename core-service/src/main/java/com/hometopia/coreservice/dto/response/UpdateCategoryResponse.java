package com.hometopia.coreservice.dto.response;

import java.util.List;

public record UpdateCategoryResponse(
        String id,
        String name,
        String description,
        ParentCategory parent,
        List<UpdateCategoryResponse> subCategories
) {
    public record ParentCategory(
            String id,
            String name
    ) {}
}
