package com.hometopia.coreservice.mapper;

import com.hometopia.coreservice.entity.embedded.Vendor;
import com.hometopia.proto.vendor.VendorResponse;
import org.mapstruct.Mapper;

@Mapper
public interface VendorMapper {
    Vendor toVendor(VendorResponse response);
}
