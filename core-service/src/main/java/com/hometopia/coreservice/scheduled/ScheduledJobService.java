package com.hometopia.coreservice.scheduled;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.utils.AppConstants;
import com.hometopia.coreservice.client.ClassificationServiceClient;
import com.hometopia.coreservice.client.dto.request.PredictAssetCategoryRequest;
import com.hometopia.coreservice.client.dto.response.PredictAssetCategoryResponse;
import com.hometopia.coreservice.entity.Asset;
import com.hometopia.coreservice.entity.Notification;
import com.hometopia.coreservice.entity.Schedule;
import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.embedded.HyperLink;
import com.hometopia.coreservice.mapper.AssetCategoryMapper;
import com.hometopia.coreservice.repository.AssetRepository;
import com.hometopia.coreservice.repository.NotificationRepository;
import com.hometopia.coreservice.repository.ScheduleRepository;
import com.hometopia.proto.asset.AssetGrpcServiceGrpc;
import com.hometopia.proto.asset.GetAssetMaintenanceCycleRequest;
import com.hometopia.proto.asset.GetAssetUsefulLifeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
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

    private final AssetCategoryMapper assetCategoryMapper;
    private final ScheduleRepository scheduleRepository;
    private final AssetRepository assetRepository;
    private final NotificationRepository notificationRepository;
    private final ClassificationServiceClient classificationServiceClient;

    @GrpcClient("rule-service")
    private AssetGrpcServiceGrpc.AssetGrpcServiceBlockingStub assetGrpcServiceBlockingStub;

    @Scheduled(cron = "${scheduling.schedule-reminder}")
    @Async
    public void createScheduleReminder() {
        log.info("Starting create schedule reminder job");

        Authentication authentication = new UsernamePasswordAuthenticationToken("System", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

        SecurityContextHolder.clearContext();

        log.info("End create schedule reminder job");
    }

    @Scheduled(cron = "${scheduling.maintenance-reminder}")
    @Async
    public void createMaintenanceReminder() {
        log.info("Starting create maintenance reminder job");

        Authentication authentication = new UsernamePasswordAuthenticationToken("System", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assetRepository.findAllInMaintenanceInterval().stream()
                .map(asset -> Notification.builder()
                        .title(MessageFormat.format(AppConstants.MAINTENANCE_REMINDER_NOTIFICATION_TITLE, asset.getName()))
                        .message(MessageFormat.format(AppConstants.MAINTENANCE_REMINDER_NOTIFICATION_MESSAGE, asset.getName()))
                        .hyperLink(new HyperLink(asset.getId(), Asset.class.getSimpleName()))
                        .user(asset.getUser())
                        .build())
                .forEach(notificationRepository::save);

        SecurityContextHolder.clearContext();

        log.info("End create maintenance reminder job");
    }

    @Scheduled(cron = "${scheduling.asset-label-maintenance-cycle}")
    @Async
    public void updateAssetLabelAndMaintenanceCycle() {
        log.info("Starting update asset label and maintenance cycle job");

        Authentication authentication = new UsernamePasswordAuthenticationToken("System", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assetRepository.findAllByLabelIsNullOrMaintenanceCycleIsNullOrUsefulLifeIsNull().stream()
                .filter(asset -> !asset.getImages().isEmpty())
                .forEach(asset -> {
                    try {
                        if (asset.getLabel() == null) {
                            ResponseEntity<PredictAssetCategoryResponse> response = classificationServiceClient
                                    .predictAssetCategory(buildPredictAssetCategoryRequest(asset.getImages().get(0)));
                            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                                AssetCategory label = response.getBody().prediction();
                                asset.setLabel(label);
                            }
                        }

                        if (asset.getMaintenanceCycle() == null) {
                            asset.setMaintenanceCycle(assetGrpcServiceBlockingStub
                                    .getMaintenanceCycle(GetAssetMaintenanceCycleRequest.newBuilder()
                                            .setCategory(assetCategoryMapper.toAssetCategoryProto(asset.getLabel()))
                                            .build())
                                    .getMaintenanceCycle());
                        }

                        if (asset.getUsefulLife() == null) {
                            asset.setUsefulLife(assetGrpcServiceBlockingStub
                                    .getUsefulLife(GetAssetUsefulLifeRequest.newBuilder()
                                            .setCategory(assetCategoryMapper.toAssetCategoryProto(asset.getLabel()))
                                            .build())
                                    .getUsefulLife());
                        }
                    } catch (Exception e) {
                        log.error("Error when processing updateAssetLabelAndMaintenanceCycle for asset {}", asset.getId());
                    }
                });

        SecurityContextHolder.clearContext();

        log.info("End update asset label and maintenance cycle job");
    }

    private PredictAssetCategoryRequest buildPredictAssetCategoryRequest(File file) {
        return new PredictAssetCategoryRequest("http://193.203.161.141:8000/api/files?fileName=%s".formatted(file.fileName()));
    }
}
