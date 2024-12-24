package com.hometopia.vendorservice.service.impl;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.vendorservice.dto.response.GetListVendorResponse;
import com.hometopia.vendorservice.mapper.VendorMapper;
import com.hometopia.vendorservice.model.Vendor;
import com.hometopia.vendorservice.repository.VendorRepository;
import com.hometopia.vendorservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public RestResponse<ListResponse<GetListVendorResponse>> getListVendors(AssetCategory category, Double lat,
                                                                            Double lon, int page, int size, boolean all) {
        Query geoPointQuery = NativeQuery.builder()
                .withQuery(q -> q.match(match -> match.field("asset_category").query(category.name())))
                .withSort(Sort.by(new GeoDistanceOrder("location", new GeoPoint(lat, lon))))
                .withPageable(all ? Pageable.unpaged() : PageRequest.of(page - 1, size))
                .build();

        SearchHits<Vendor> searchHits = elasticsearchOperations.search(geoPointQuery, Vendor.class);

        Page<GetListVendorResponse> vendors = SearchHitSupport.searchPageFor(searchHits, geoPointQuery.getPageable())
                .map(SearchHit::getContent).map(vendorMapper::toGetListVendorResponse);

        return RestResponse.ok(ListResponse.of(vendors));
    }

    @Override
    public void saveListVendors(List<com.hometopia.commons.message.Vendor> vendors) {
        vendorRepository.saveAll(vendorMapper.toListVendors(vendors));
    }
}
