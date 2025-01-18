package com.hometopia.coreservice.mapper;

import com.hometopia.coreservice.dto.request.CreateScheduleRequest;
import com.hometopia.coreservice.dto.request.UpdateScheduleRequest;
import com.hometopia.coreservice.dto.response.CreateScheduleResponse;
import com.hometopia.coreservice.dto.response.GetListScheduleResponse;
import com.hometopia.coreservice.dto.response.GetOneScheduleResponse;
import com.hometopia.coreservice.dto.response.UpdateScheduleResponse;
import com.hometopia.coreservice.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = AssetMapper.class)
public interface ScheduleMapper {
    @Mapping(source = "assetId", target = "asset")
    Schedule toSchedule(CreateScheduleRequest request);

    CreateScheduleResponse toCreateScheduleResponse(Schedule schedule);

    GetListScheduleResponse toGetListScheduleResponse(Schedule schedule);

    GetOneScheduleResponse toGetOneScheduleResponse(Schedule schedule);

    @Mapping(source = "assetId", target = "asset")
    Schedule updateSchedule(@MappingTarget Schedule schedule, UpdateScheduleRequest request);

    UpdateScheduleResponse toUpdateScheduleResponse(Schedule schedule);
}
