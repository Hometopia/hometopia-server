package com.hometopia.vendorservice.mapper;

import com.hometopia.vendorservice.dto.response.GetListBrandResponse;
import com.hometopia.vendorservice.model.Brand;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {
    GetListBrandResponse toGetListBrandResponse(Brand brand);
}
