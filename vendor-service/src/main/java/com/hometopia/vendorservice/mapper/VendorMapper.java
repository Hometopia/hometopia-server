package com.hometopia.vendorservice.mapper;

import com.hometopia.vendorservice.dto.response.GetListVendorResponse;
import com.hometopia.vendorservice.model.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VendorMapper {
    Vendor toVendor(com.hometopia.commons.message.Vendor vendor);

    @Mapping(target = "address", source = "address.line")
    GetListVendorResponse toGetListVendorResponse(Vendor vendor);
}
