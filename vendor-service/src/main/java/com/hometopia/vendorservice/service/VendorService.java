package com.hometopia.vendorservice.service;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.vendorservice.dto.response.GetListVendorResponse;

import java.util.List;

public interface VendorService {
    ListResponse<GetListVendorResponse> getListVendors(AssetCategory category, Double lat, Double lon);
    void saveListVendors(List<com.hometopia.commons.message.Vendor> vendors);
}