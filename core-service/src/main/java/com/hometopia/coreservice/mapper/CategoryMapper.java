package com.hometopia.coreservice.mapper;

import com.hometopia.commons.mapper.ReferenceMapper;
import com.hometopia.coreservice.dto.request.CreateListCategoriesRequest;
import com.hometopia.coreservice.dto.request.UpdateCategoryRequest;
import com.hometopia.coreservice.dto.response.CreateCategoryResponse;
import com.hometopia.coreservice.dto.response.GetListCategoryResponse;
import com.hometopia.coreservice.dto.response.SuggestedCategoryResponse;
import com.hometopia.coreservice.dto.response.UpdateCategoryResponse;
import com.hometopia.coreservice.entity.Category;
import com.hometopia.coreservice.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.stream.Collectors;

@Mapper(uses = ReferenceMapper.class)
public interface CategoryMapper {

    Category toCategory(String categoryId);

    @Mapping(source = "subCategoriesList", target = "subCategories")
    SuggestedCategoryResponse toSuggestedCategoryResponse(com.hometopia.proto.category.CategoryResponse categoryResponse);

    SuggestedCategoryResponse toSuggestedCategoryResponse(Category category);

    CreateCategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "numberOfAssets", expression = "java(category.getAssets().size())")
    GetListCategoryResponse toGetListCategoryResponse(Category category);

    @Mapping(source = "parentId", target = "parent")
    @Mapping(source = "subCategoryIds", target = "subCategories")
    Category updateCategory(@MappingTarget Category category, UpdateCategoryRequest request);

    UpdateCategoryResponse toUpdateCategoryResponse(Category category);

    default Category toCategory(CreateListCategoriesRequest.Category request, Category parent, User user) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        category.setUser(user);
        category.setParent(parent);
        category.setSubCategories(request.subCategories().stream()
                .map(subCategory -> toCategory(subCategory, category, user))
                .collect(Collectors.toSet()));
        return category;
    }

    @AfterMapping
    default void attachParentCategory(@MappingTarget Category category, UpdateCategoryRequest request) {
        category.getSubCategories().forEach(child -> child.setParent(category));
    }
}
