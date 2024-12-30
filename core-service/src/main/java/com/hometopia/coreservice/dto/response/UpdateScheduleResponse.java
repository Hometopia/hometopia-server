package com.hometopia.coreservice.dto.response;

import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.embedded.Vendor;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public record UpdateScheduleResponse(
        String id,
        String title,
        LocalDateTime start,
        LocalDateTime end,
        Vendor vendor,
        BigDecimal cost,
        ArrayList<File> documents,
        ScheduleType type,
        Asset asset
) {

    public record Asset(
            String id,
            String name
    ) {}

}