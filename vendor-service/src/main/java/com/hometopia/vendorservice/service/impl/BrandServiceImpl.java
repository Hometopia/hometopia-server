package com.hometopia.vendorservice.service.impl;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.vendorservice.dto.response.GetListBrandResponse;
import com.hometopia.vendorservice.mapper.BrandMapper;
import com.hometopia.vendorservice.model.Brand;
import com.hometopia.vendorservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandMapper brandMapper;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public RestResponse<ListResponse<GetListBrandResponse>> getListBrand(int page, int size, String filter, boolean all) {
        Query fuzzyQuery = NativeQuery.builder()
                .withQuery(q -> q.fuzzy(builder -> builder.field("name").value(filter).fuzziness("AUTO")))
                .withPageable(all ? Pageable.unpaged() : PageRequest.of(page - 1, size))
                .build();

        SearchHits<Brand> searchHits = elasticsearchOperations.search(fuzzyQuery, Brand.class);

        return RestResponse.ok(ListResponse.of(searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(brandMapper::toGetListBrandResponse)
                .toList()));
    }
}
