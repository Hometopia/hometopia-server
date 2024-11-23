package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateScheduleRequest;
import com.hometopia.coreservice.dto.request.UpdateScheduleRequest;
import com.hometopia.coreservice.dto.response.CreateScheduleResponse;
import com.hometopia.coreservice.dto.response.GetListScheduleResponse;
import com.hometopia.coreservice.dto.response.GetOneScheduleResponse;
import com.hometopia.coreservice.dto.response.UpdateScheduleResponse;

import java.util.List;

public interface ScheduleService {
    RestResponse<ListResponse<GetListScheduleResponse>> getListSchedules(int page, int size, String sort, String filter, boolean all);
    RestResponse<GetOneScheduleResponse> getOneSchedule(String id);
    RestResponse<CreateScheduleResponse> createSchedule(CreateScheduleRequest request);
    RestResponse<UpdateScheduleResponse> updateSchedule(String id, UpdateScheduleRequest request);
    void deleteSchedule(String id);
    void deleteListSchedules(List<String> ids);
}
