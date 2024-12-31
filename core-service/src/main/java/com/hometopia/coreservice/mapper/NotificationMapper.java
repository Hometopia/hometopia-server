package com.hometopia.coreservice.mapper;

import com.hometopia.coreservice.dto.response.GetListNotificationResponse;
import com.hometopia.coreservice.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NotificationMapper {
    @Mapping(target = "isRead", source = "read")
    GetListNotificationResponse toGetListNotificationResponse(Notification notification);
}
