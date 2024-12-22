package com.hometopia.vendorservice.service.impl;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.message.Vendor;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.vendorservice.dto.response.GetListVendorResponse;
import com.hometopia.vendorservice.mapper.VendorMapper;
import com.hometopia.vendorservice.repository.VendorRepository;
import com.hometopia.vendorservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    @Override
    public ListResponse<GetListVendorResponse> getListVendors(AssetCategory category, Double lat, Double lon) {
        return null;
    }

    @Override
    public void saveListVendors(List<Vendor> vendors) {
        vendorRepository.saveAll(vendorMapper.toListVendors(vendors));
    }
}
