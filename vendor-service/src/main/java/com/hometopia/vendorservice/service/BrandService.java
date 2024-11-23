package com.hometopia.vendorservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.vendorservice.dto.response.GetListBrandResponse;

public interface BrandService {
    RestResponse<ListResponse<GetListBrandResponse>> getListBrand(int page, int size, String query, boolean all);
}
