package com.hometopia.coreservice.dto.response;

import com.hometopia.coreservice.entity.embedded.HyperLink;

import java.time.Instant;

public record GetListNotificationResponse(
        String id,
        Instant createdAt,
        String title,
        String message,
        boolean isRead,
        HyperLink hyperLink
) {}
