package com.hometopia.coreservice.service;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.coreservice.dto.response.GetListNotificationResponse;

import java.util.List;

public interface NotificationService {
    RestResponse<ListResponse<GetListNotificationResponse>> getListNotifications(int page, int size, String sort, boolean all);
    void markAsRead(List<String> ids);
}
