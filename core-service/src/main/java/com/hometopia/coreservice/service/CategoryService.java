package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateListCategoriesRequest;
import com.hometopia.coreservice.dto.request.UpdateCategoryRequest;
import com.hometopia.coreservice.dto.response.CreateCategoryResponse;
import com.hometopia.coreservice.dto.response.GetListCategoryResponse;
import com.hometopia.coreservice.dto.response.UpdateCategoryResponse;

import java.util.List;

public interface CategoryService {
    RestResponse<ListResponse<GetListCategoryResponse>> getListCategories(int page, int size, String sort, String filter, boolean all);
    RestResponse<ListResponse<CreateCategoryResponse>> createListCategories(CreateListCategoriesRequest request);
    RestResponse<UpdateCategoryResponse> updateCategory(String id, UpdateCategoryRequest request);
    void deleteCategory(String id);
    void deleteListCategories(List<String> ids);
}
