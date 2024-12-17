package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.request.CreateScheduleRequest;
import com.hometopia.coreservice.dto.request.UpdateScheduleRequest;
import com.hometopia.coreservice.dto.response.CreateScheduleResponse;
import com.hometopia.coreservice.dto.response.GetListScheduleResponse;
import com.hometopia.coreservice.dto.response.GetOneScheduleResponse;
import com.hometopia.coreservice.dto.response.UpdateScheduleResponse;
import com.hometopia.coreservice.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<ListResponse<GetListScheduleResponse>>> getListSchedules(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,desc") String sort,
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) boolean all
    ) {
        return ResponseEntity.ok(scheduleService.getListSchedules(page, size, sort, filter, all));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<GetOneScheduleResponse>> getOneSchedule(@PathVariable String id) {
        return ResponseEntity.ok(scheduleService.getOneSchedule(id));
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<CreateScheduleResponse>> createSchedule(@RequestBody @Valid CreateScheduleRequest request) {
        RestResponse<CreateScheduleResponse> response = scheduleService.createSchedule(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<UpdateScheduleResponse>> updateSchedule(@PathVariable String id,
                                                                               @RequestBody @Valid UpdateScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSchedule(@RequestParam List<String> ids) {
        scheduleService.deleteListSchedules(ids);
        return ResponseEntity.noContent().build();
    }
}
