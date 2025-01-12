package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateAssetRequest;
import com.hometopia.coreservice.dto.request.UpdateAssetRequest;
import com.hometopia.coreservice.dto.response.CreateAssetResponse;
import com.hometopia.coreservice.dto.response.GetListAssetResponse;
import com.hometopia.coreservice.dto.response.GetOneAssetResponse;
import com.hometopia.coreservice.dto.response.UpdateAssetResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.QAsset;
import com.hometopia.coreservice.entity.enumeration.AssetStatus;
import com.hometopia.coreservice.mapper.AssetMapper;
import com.hometopia.coreservice.repository.AssetRepository;
import com.hometopia.coreservice.repository.UserRepository;
import com.hometopia.coreservice.service.AssetLifeCycleService;
import com.hometopia.coreservice.service.AssetService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetMapper assetMapper;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AssetLifeCycleService assetLifeCycleService;

    @Override
    public RestResponse<ListResponse<GetListAssetResponse>> getListAssets(int page, int size, String sort, String filter, boolean all) {
        Specification<Asset> sortable = RSQLJPASupport.toSort(sort);
        Specification<Asset> filterable = RSQLJPASupport.toSpecification(
                Optional.ofNullable(filter)
                        .map(f -> URLDecoder.decode(f, StandardCharsets.UTF_8))
                        .orElse(null)
        );
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<GetListAssetResponse> responses = assetRepository
                .findAll(sortable.and(filterable).and((Specification<Asset>) (root, query, cb) ->
                        cb.equal(root.get(QAsset.asset.user.getMetadata().getName()),
                                userRepository.getReferenceById(SecurityUtils.getCurrentUserId()))
                ), pageable)
                .map(assetMapper::toGetListAssetResponse);
        return RestResponse.ok(ListResponse.of(responses));
    }

    @Override
    public RestResponse<GetOneAssetResponse> getOneAsset(String id) {
        return assetRepository.findById(id)
                .map(assetMapper::toGetOneAssetResponse)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));
    }

    @Override
    @Transactional
    public RestResponse<CreateAssetResponse> createAsset(CreateAssetRequest request) {
        Asset asset = assetMapper.toAsset(request, userRepository.getReferenceById(SecurityUtils.getCurrentUserId()));
        assetRepository.save(asset);
        assetLifeCycleService.createAssetLifeCycle(asset);
        return RestResponse.ok(assetMapper.toCreateAssetResponse(asset));
    }

    @Override
    @Transactional
    public RestResponse<UpdateAssetResponse> updateAsset(String id, UpdateAssetRequest request) {
        Asset asset = assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));
        AssetStatus previousStatus = asset.getStatus();
        assetMapper.updateAsset(asset, request);
        if (previousStatus != asset.getStatus()) {
            assetLifeCycleService.createAssetLifeCycle(asset);
        }
        return RestResponse.ok(assetMapper.toUpdateAssetResponse(asset));
    }

    @Override
    @Transactional
    public void deleteAsset(String id) {
        assetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteListAssets(List<String> ids) {
        assetRepository.deleteAllById(ids);
    }
}
