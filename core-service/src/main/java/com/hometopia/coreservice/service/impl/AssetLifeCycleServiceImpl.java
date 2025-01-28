package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.GetListAssetLifeCycleResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.AssetLifeCycle;
import com.hometopia.coreservice.entity.Location;
import com.hometopia.coreservice.entity.Schedule;
import com.hometopia.coreservice.mapper.AssetLifeCycleMapper;
import com.hometopia.coreservice.repository.AssetLifeCycleRepository;
import com.hometopia.coreservice.service.AssetLifeCycleService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
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
    public void createAssetLifeCycleByStatus(Asset asset) {
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
            case SOLD -> assetLifeCycle.setDescription("%s đuợc bán.".formatted(asset.getName()));
        }
        assetLifeCycleRepository.save(assetLifeCycle);
    }

    @Override
    public void createAssetLifeCycleByLocation(Asset asset, Location previousLocation) {
        AssetLifeCycle assetLifeCycle = new AssetLifeCycle();
        assetLifeCycle.setTimestamp(Instant.now());
        assetLifeCycle.setAsset(asset);
        if (previousLocation != null && asset.getLocation() != null) {
            assetLifeCycle.setDescription("%s được chuyển từ %s sang %s".formatted(asset.getName(),
                    previousLocation.getName(), asset.getLocation().getName()));
        } else if (previousLocation != null) {
            assetLifeCycle.setDescription("%s được di chuyển ra khỏi %s".formatted(asset.getName(),
                    previousLocation.getName()));
        } else if (asset.getLocation() != null) {
            assetLifeCycle.setDescription("%s được đặt tại %s".formatted(asset.getName(), asset.getLocation().getName()));
        }

        if (assetLifeCycle.getDescription() != null) {
            assetLifeCycleRepository.save(assetLifeCycle);
        }
    }

    @Override
    public void createAssetLifeCycleBySchedule(Schedule schedule) {
        AssetLifeCycle assetLifeCycle = assetLifeCycleRepository.findBySchedule(schedule)
                .orElse(new AssetLifeCycle());
        assetLifeCycle.setTimestamp(schedule.getStart().atZone(ZoneId.systemDefault()).toInstant());
        assetLifeCycle.setAsset(schedule.getAsset());
        assetLifeCycle.setSchedule(schedule);
        assetLifeCycle.setDescription(getDescriptionBySchedule(schedule));
        if (assetLifeCycle.getId() == null) {
            assetLifeCycleRepository.save(assetLifeCycle);
        }
    }

    private String getDescriptionBySchedule(Schedule schedule) {
        StringBuilder sb = new StringBuilder();
        switch (schedule.getType()) {
            case REPAIR:
                sb.append("Tài sản được sửa chữa");
                break;
            case MAINTENANCE:
                sb.append("Tài sản được bảo trì");
                break;
        }
        if (schedule.getVendor() != null) {
            sb.append("tại %s".formatted(schedule.getVendor().name()));
        }
        return sb.toString();
    }
}
