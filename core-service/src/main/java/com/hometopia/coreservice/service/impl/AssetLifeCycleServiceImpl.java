package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.GetListAssetLifeCycleResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.AssetLifeCycle;
import com.hometopia.coreservice.mapper.AssetLifeCycleMapper;
import com.hometopia.coreservice.repository.AssetLifeCycleRepository;
import com.hometopia.coreservice.service.AssetLifeCycleService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetLifeCycleServiceImpl implements AssetLifeCycleService {

    private final AssetLifeCycleMapper assetLifeCycleMapper;
    private final AssetLifeCycleRepository assetLifeCycleRepository;

    @Override
    @Transactional(readOnly = true)
    public RestResponse<ListResponse<GetListAssetLifeCycleResponse>> getListAssetLifeCycle(String filter) {
        Specification<AssetLifeCycle> sortable = RSQLJPASupport.toSort("timestamp,asc");
        Specification<AssetLifeCycle> filterable = RSQLJPASupport.toSpecification(filter);
        List<GetListAssetLifeCycleResponse> responses = assetLifeCycleRepository.findAll(filterable.and(sortable))
                .stream()
                .map(assetLifeCycleMapper::toGetListAssetLifeCycleResponse)
                .toList();

        return RestResponse.ok(ListResponse.of(responses));
    }

    @Override
    public void createAssetLifeCycle(Asset asset) {
        AssetLifeCycle assetLifeCycle = new AssetLifeCycle();
        assetLifeCycle.setTimestamp(Instant.now());
        assetLifeCycle.setAsset(asset);
        switch (asset.getStatus()) {
            case IN_USE -> assetLifeCycle.setDescription(asset.getVersion() == 0
                    ? "%s đuợc đưa vào sử dụng.".formatted(asset.getName())
                    : "%s đuợc đưa quay lại sử dụng.".formatted(asset.getName()));
            case BROKEN -> assetLifeCycle.setDescription("%s bị hỏng.".formatted(asset.getName()));
            case UNDER_REPAIR -> assetLifeCycle.setDescription("%s được sửa chữa.".formatted(asset.getName()));
            case MAINTENANCE -> assetLifeCycle.setDescription("%s đuợc bảo trì.".formatted(asset.getName()));
            case RESERVED -> assetLifeCycle.setDescription("%s đuợc cất vào kho.".formatted(asset.getName()));
            case FOR_LOAN -> assetLifeCycle.setDescription("%s đuợc mang cho mượn.".formatted(asset.getName()));
            case SOLD ->  assetLifeCycle.setDescription("%s đuợc bán.".formatted(asset.getName()));
        }
        assetLifeCycleRepository.save(assetLifeCycle);
    }
}
