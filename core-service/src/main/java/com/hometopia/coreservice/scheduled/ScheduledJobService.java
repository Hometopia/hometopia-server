package com.hometopia.coreservice.scheduled;

import com.hometopia.commons.utils.AppConstants;
import com.hometopia.coreservice.entity.Notification;
import com.hometopia.coreservice.entity.Schedule;
import com.hometopia.coreservice.entity.embedded.HyperLink;
import com.hometopia.coreservice.repository.NotificationRepository;
import com.hometopia.coreservice.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ScheduledJobService {

    private final ScheduleRepository scheduleRepository;
    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "${scheduling.create-notification}")
    @Async
    public void createNotification() {
        log.info("Starting create notification job");

        Authentication authentication = new UsernamePasswordAuthenticationToken("System", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        createScheduleReminder();

        SecurityContextHolder.clearContext();

        log.info("End create notification job");
    }

    private void createScheduleReminder() {
        scheduleRepository.findByStartBetween(LocalDateTime.now(), LocalDateTime.now().plusWeeks(1)).stream()
                .map(schedule -> Notification.builder()
                        .title(MessageFormat.format(AppConstants.SCHEDULE_REMINDER_NOTIFICATION_TITLE,
                                AppConstants.DATE_FORMATTER.format(schedule.getStart())))
                        .message(MessageFormat.format(AppConstants.SCHEDULE_REMINDER_NOTIFICATION_MESSAGE,
                                AppConstants.DATE_FORMATTER.format(schedule.getStart()), schedule.getVendor().name()))
                        .hyperLink(new HyperLink(schedule.getId(), Schedule.class.getSimpleName()))
                        .user(schedule.getAsset().getUser())
                        .build())
                .forEach(notificationRepository::save);
    }
}
