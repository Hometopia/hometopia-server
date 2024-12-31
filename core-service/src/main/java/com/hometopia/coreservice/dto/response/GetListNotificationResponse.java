package com.hometopia.coreservice.dto.response;

import com.hometopia.coreservice.entity.embedded.HyperLink;

public record GetListNotificationResponse(
        String id,
        String title, String message,
        boolean isRead,
        HyperLink hyperLink
) {}
