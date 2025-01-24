package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateAssetRequest;
import com.hometopia.coreservice.dto.request.UpdateAssetRequest;
import com.hometopia.coreservice.dto.response.CreateAssetResponse;
import com.hometopia.coreservice.dto.response.GetAssetDepreciationResponse;
import com.hometopia.coreservice.dto.response.GetListAssetResponse;
import com.hometopia.coreservice.dto.response.GetOneAssetResponse;
import com.hometopia.coreservice.dto.response.UpdateAssetResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.Location;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        assetLifeCycleService.createAssetLifeCycleByStatus(asset);
        assetLifeCycleService.createAssetLifeCycleByLocation(asset, null);
        return RestResponse.ok(assetMapper.toCreateAssetResponse(asset));
    }

    @Override
    @Transactional
    public RestResponse<UpdateAssetResponse> updateAsset(String id, UpdateAssetRequest request) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));
        AssetStatus previousStatus = asset.getStatus();
        Location previousLocation = asset.getLocation();
        assetMapper.updateAsset(asset, request);
        if (previousStatus != asset.getStatus()) {
            assetLifeCycleService.createAssetLifeCycleByStatus(asset);
        }
        if (!Objects.equals(previousLocation, asset.getLocation())) {
            assetLifeCycleService.createAssetLifeCycleByLocation(asset, previousLocation);
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

    @Override
    public RestResponse<GetAssetDepreciationResponse> getAssetDepreciation(String id) {
        Asset asset = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", id));

        if (asset.getUsefulLife() == null) {
            throw new RuntimeException("Asset useful life is required");
        }

        List<GetAssetDepreciationResponse.Depreciation> straightLineDepreciation = new ArrayList<>();
        List<GetAssetDepreciationResponse.Depreciation> decliningBalanceDepreciation = new ArrayList<>();
        BigDecimal straightLineDepreciationValue = asset.getPurchasePrice().divide(BigDecimal.valueOf(asset.getUsefulLife()), RoundingMode.HALF_UP);
        BigDecimal straightLineDepreciationRate = straightLineDepreciationValue.divide(asset.getPurchasePrice(), RoundingMode.HALF_UP);
        BigDecimal decliningBalanceDepreciationRate = straightLineDepreciationRate.multiply(BigDecimal
                .valueOf(asset.getUsefulLife() <= 4 ? 1.5 : 2));
        BigDecimal residualValue = asset.getPurchasePrice();

        straightLineDepreciation.add(new GetAssetDepreciationResponse.Depreciation(
                asset.getPurchaseDate().getYear(), residualValue));
        for (int i = 1; i <= asset.getUsefulLife() - 1; i++) {
            residualValue = residualValue.subtract(straightLineDepreciationValue);
            straightLineDepreciation.add(new GetAssetDepreciationResponse.Depreciation(
                    asset.getPurchaseDate().getYear() + i, residualValue));
        }

        residualValue = asset.getPurchasePrice();
        for (int i = 0; i < asset.getUsefulLife(); i++) {
            decliningBalanceDepreciation.add(new GetAssetDepreciationResponse.Depreciation(
                    asset.getPurchaseDate().getYear() + i, residualValue));
            if (i != asset.getUsefulLife() - 1) {
                residualValue = residualValue.multiply(BigDecimal.ONE.subtract(decliningBalanceDepreciationRate))
                        .compareTo(residualValue.divide(BigDecimal.valueOf(asset.getUsefulLife() - i - 1), RoundingMode.HALF_UP)) >= 0
                        ? residualValue.multiply(BigDecimal.ONE.subtract(decliningBalanceDepreciationRate))
                        : residualValue.divide(BigDecimal.valueOf(asset.getUsefulLife() - i - 1), RoundingMode.HALF_UP);

            }
        }

        return RestResponse.ok(new GetAssetDepreciationResponse(straightLineDepreciation, decliningBalanceDepreciation));
    }

    @Override
    public Map<Asset, BigDecimal> getAssetsCurrentValue(String userId) {
        return assetRepository.findAllByLabelIsNotNullAndMaintenanceCycleIsNotNullAndUsefulLifeIsNotNullAndUser(
                        userRepository.getReferenceById(SecurityUtils.getCurrentUserId())).stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        asset -> getAssetCurrentValue(asset.getId())
                ));
    }

    private BigDecimal getAssetCurrentValue(String id) {
        return getAssetDepreciation(id).data().decliningBalanceDepreciation().stream()
                .filter(d -> d.year().equals(LocalDate.now().getYear()))
                .findFirst()
                .map(GetAssetDepreciationResponse.Depreciation::value)
                .orElse(BigDecimal.ZERO);
    }
}
