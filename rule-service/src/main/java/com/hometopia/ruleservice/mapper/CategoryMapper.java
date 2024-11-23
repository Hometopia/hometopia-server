package com.hometopia.ruleservice.mapper;

import com.hometopia.proto.category.CategoryResponse;
import com.hometopia.ruleservice.dto.rule.Category;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface CategoryMapper {
    @Mapping(source = "subCategories", target = "subCategoriesList")
    CategoryResponse toCategoryResponse(Category category);
}
