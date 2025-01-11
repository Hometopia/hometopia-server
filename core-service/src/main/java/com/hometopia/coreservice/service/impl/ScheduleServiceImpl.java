package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.request.CreateScheduleRequest;
import com.hometopia.coreservice.dto.request.UpdateScheduleRequest;
import com.hometopia.coreservice.dto.response.CreateScheduleResponse;
import com.hometopia.coreservice.dto.response.GetListScheduleResponse;
import com.hometopia.coreservice.dto.response.GetOneScheduleResponse;
import com.hometopia.coreservice.dto.response.UpdateScheduleResponse;
import com.hometopia.coreservice.entity.QSchedule;
import com.hometopia.coreservice.entity.Schedule;
import com.hometopia.coreservice.mapper.ScheduleMapper;
import com.hometopia.coreservice.repository.AssetRepository;
import com.hometopia.coreservice.repository.ScheduleRepository;
import com.hometopia.coreservice.service.ScheduleService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleMapper scheduleMapper;
    private final AssetRepository assetRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public RestResponse<ListResponse<GetListScheduleResponse>> getListSchedules(int page, int size, String sort, String filter, boolean all) {
        Specification<Schedule> sortable = RSQLJPASupport.toSort(sort);
        Specification<Schedule> filterable = RSQLJPASupport.toSpecification(filter);
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
}
