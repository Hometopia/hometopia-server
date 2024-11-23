package com.hometopia.ruleservice.service;

public interface CategoryService {
    com.hometopia.proto.category.GetListCategoryResponse getListCategories(com.hometopia.proto.category.GetListCategoryRequest request);
}
