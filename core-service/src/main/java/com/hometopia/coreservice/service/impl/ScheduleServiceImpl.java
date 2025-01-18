package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.AppConstants;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateScheduleRequest;
import com.hometopia.coreservice.dto.request.UpdateScheduleRequest;
import com.hometopia.coreservice.dto.response.CreateScheduleResponse;
import com.hometopia.coreservice.dto.response.GetListScheduleResponse;
import com.hometopia.coreservice.dto.response.GetOneScheduleResponse;
import com.hometopia.coreservice.dto.response.SuggestedMaintenanceScheduleResponse;
import com.hometopia.coreservice.dto.response.UpdateScheduleResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.QSchedule;
import com.hometopia.coreservice.entity.Schedule;
import com.hometopia.coreservice.entity.embedded.Vendor;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import com.hometopia.coreservice.mapper.AssetCategoryMapper;
import com.hometopia.coreservice.mapper.ScheduleMapper;
import com.hometopia.coreservice.mapper.VendorMapper;
import com.hometopia.coreservice.repository.AssetRepository;
import com.hometopia.coreservice.repository.ScheduleRepository;
import com.hometopia.coreservice.service.ScheduleService;
import com.hometopia.proto.vendor.GetListVendorRequest;
import com.hometopia.proto.vendor.GetListVendorResponse;
import com.hometopia.proto.vendor.VendorGrpcServiceGrpc;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final AssetCategoryMapper assetCategoryMapper;
    private final VendorMapper vendorMapper;
    private final AssetRepository assetRepository;
    private final ScheduleRepository scheduleRepository;

    @GrpcClient("vendor-service")
    private VendorGrpcServiceGrpc.VendorGrpcServiceBlockingStub vendorGrpcServiceBlockingStub;

    @Override
    public RestResponse<ListResponse<GetListScheduleResponse>> getListSchedules(int page, int size, String sort, String filter, boolean all) {
        Specification<Schedule> sortable = RSQLJPASupport.toSort(sort);
        Specification<Schedule> filterable = RSQLJPASupport.toSpecification(
                Optional.ofNullable(filter)
                        .map(f -> URLDecoder.decode(f, StandardCharsets.UTF_8))
                        .orElse(null)
        );
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<GetListScheduleResponse> schedules = scheduleRepository.findAll(sortable.and(filterable)
                        .and((Specification<Schedule>) (root, query, cb) ->
                                root.get(QSchedule.schedule.asset.getMetadata().getName())
                                        .in(assetRepository.findAllByUserId(SecurityUtils.getCurrentUserId()))
                        ), pageable)
                .map(scheduleMapper::toGetListScheduleResponse);

        return RestResponse.ok(ListResponse.of(schedules));
    }

    @Override
    public RestResponse<GetOneScheduleResponse> getOneSchedule(String id) {
        return scheduleRepository.findById(id)
                .map(scheduleMapper::toGetOneScheduleResponse)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));
    }

    @Override
    @Transactional
    public RestResponse<CreateScheduleResponse> createSchedule(CreateScheduleRequest request) {
        Schedule schedule = scheduleMapper.toSchedule(request);
        scheduleRepository.save(schedule);
        return RestResponse.ok(scheduleMapper.toCreateScheduleResponse(schedule));
    }

    @Override
    @Transactional
    public RestResponse<UpdateScheduleResponse> updateSchedule(String id, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));
        scheduleMapper.updateSchedule(schedule, request);
        return RestResponse.ok(scheduleMapper.toUpdateScheduleResponse(schedule));
    }

    @Override
    @Transactional
    public void deleteSchedule(String id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteListSchedules(List<String> ids) {
        scheduleRepository.deleteAllById(ids);
    }

    @Override
    public RestResponse<ListResponse<SuggestedMaintenanceScheduleResponse>> getListSuggestedMaintenanceSchedules(String assetId, Double lat, Double lon) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", "id", assetId));

        if (asset.getLabel() == null || asset.getMaintenanceCycle() == null || asset.getUsefulLife() == null) {
            throw new RuntimeException("Asset category and maintenance cycle are required");
        }

        List<SuggestedMaintenanceScheduleResponse> suggested = new ArrayList<>();
        GetListVendorResponse response = vendorGrpcServiceBlockingStub.getListVendor(GetListVendorRequest.newBuilder()
                .setCategory(assetCategoryMapper.toAssetCategoryProto(asset.getLabel()))
                .setLat(lat)
                .setLon(lon)
                .build());
        List<Vendor> vendors = response.getVendorsList().stream().map(vendorMapper::toVendor).toList();

        LocalDate purchaseDate = asset.getPurchaseDate();
        LocalDate endOfLifeCycle = purchaseDate.plusYears(asset.getUsefulLife());
        LocalDate start = purchaseDate;

        while (start.isAfter(LocalDate.now())) {
            start = start.plusMonths(asset.getMaintenanceCycle());
        }

        while (start.isBefore(endOfLifeCycle)) {
            suggested.add(SuggestedMaintenanceScheduleResponse.builder()
                    .title(MessageFormat.format(AppConstants.SUGGESTED_MAINTENANCE_SCHEDULE_TITLE,
                            AppConstants.DATE_FORMATTER.format(start), asset.getName()))
                    .start(LocalDateTime.of(start, LocalTime.MIDNIGHT))
                    .vendor(vendors.get((int) (Math.random() * (vendors.size() - 1))))
                    .type(ScheduleType.MAINTENANCE)
                    .assetId(assetId)
                    .build());
            start = start.plusMonths(asset.getMaintenanceCycle());
        }

        return RestResponse.ok(ListResponse.of(suggested));
    }
}
