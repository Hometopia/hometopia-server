package com.hometopia.vendorservice.repository;

import com.hometopia.vendorservice.model.Vendor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VendorRepository extends ElasticsearchRepository<Vendor, String> {
}
