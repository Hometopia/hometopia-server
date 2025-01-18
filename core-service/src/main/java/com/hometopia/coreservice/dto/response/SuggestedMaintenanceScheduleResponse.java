package com.hometopia.coreservice.dto.response;

import com.hometopia.coreservice.entity.embedded.Vendor;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SuggestedMaintenanceScheduleResponse(
        String title,
        LocalDateTime start,
        Vendor vendor,
        ScheduleType type,
        String assetId
) {}
