package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.commons.response.RestResponse;
import com.hometopia.commons.utils.SecurityUtils;
import com.hometopia.coreservice.dto.response.GetListNotificationResponse;
import com.hometopia.coreservice.entity.Notification;
import com.hometopia.coreservice.entity.QNotification;
import com.hometopia.coreservice.mapper.NotificationMapper;
import com.hometopia.coreservice.repository.NotificationRepository;
import com.hometopia.coreservice.repository.UserRepository;
import com.hometopia.coreservice.service.NotificationService;
import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public RestResponse<ListResponse<GetListNotificationResponse>> getListNotifications(int page, int size, String sort, boolean all) {
        Specification<Notification> sortable = RSQLJPASupport.toSort(sort);
        Pageable pageable = all ? Pageable.unpaged() : PageRequest.of(page - 1, size);
        Page<GetListNotificationResponse> schedules = notificationRepository.findAll(sortable
                        .and((Specification<Notification>) (root, query, cb) -> cb.equal(
                                root.get(QNotification.notification.user.getMetadata().getName()),
                                userRepository.getReferenceById(SecurityUtils.getCurrentUserId()))
                        ), pageable)
                .map(notificationMapper::toGetListNotificationResponse);

        return RestResponse.ok(ListResponse.of(schedules));
    }

    @Override
    @Transactional
    public void markAsRead(List<String> ids) {
        notificationRepository.findAllById(ids).forEach(notification -> notification.setRead(true));
    }
}
