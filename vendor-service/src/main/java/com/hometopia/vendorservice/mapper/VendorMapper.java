package com.hometopia.vendorservice.mapper;

import com.hometopia.vendorservice.dto.response.GetListVendorResponse;
import com.hometopia.vendorservice.model.Vendor;
import org.mapstruct.Mapper;

@Mapper
public interface VendorMapper {
    Vendor toVendor(com.hometopia.commons.message.Vendor vendor);
    GetListVendorResponse toGetListVendorResponse(Vendor vendor);
}
