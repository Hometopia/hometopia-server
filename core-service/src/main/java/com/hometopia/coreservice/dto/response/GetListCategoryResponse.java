package com.hometopia.coreservice.dto.response;

public record GetListCategoryResponse(
        String id,
        String name,
        String description,
        Integer numberOfAssets,
        ParentCategory parent
) {
    public record ParentCategory(
            String id,
            String name
    ) {}
}
