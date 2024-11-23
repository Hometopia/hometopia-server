package com.hometopia.coreservice.dto.request;

import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public record CreateScheduleRequest(
        @NotEmpty
        String title,
        @NotNull
        @FutureOrPresent
        LocalDateTime start,
        @NotNull
        @FutureOrPresent
        LocalDateTime end,
        @NotEmpty
        String vendor,
        @Positive
        BigDecimal cost,
        @NotNull
        ArrayList<File> documents,
        @NotNull
        ScheduleType type,
        String assetId
) {}