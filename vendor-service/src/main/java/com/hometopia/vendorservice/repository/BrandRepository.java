package com.hometopia.vendorservice.repository;

import com.hometopia.vendorservice.model.Brand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BrandRepository extends ElasticsearchRepository<Brand, String> {
}
