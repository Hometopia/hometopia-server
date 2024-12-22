package com.hometopia.vendorservice.mapper;

import com.hometopia.vendorservice.dto.response.GetListVendorResponse;
import com.hometopia.vendorservice.model.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper
public interface VendorMapper {
    Vendor toVendor(com.hometopia.commons.message.Vendor vendor);

    List<Vendor> toListVendors(List<com.hometopia.commons.message.Vendor> vendors);

    @Mapping(target = "address", source = "address.line")
    GetListVendorResponse toGetListVendorResponse(Vendor vendor);

    default GeoPoint togeoPoint(com.hometopia.commons.message.Vendor.GeoPoint geoPoint) {
        return new GeoPoint(geoPoint.lat(), geoPoint.lon());
    }
}
