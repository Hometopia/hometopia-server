package com.hometopia.scheduledservice.job;

import com.hometopia.commons.message.Vendor;
import com.hometopia.scheduledservice.util.HometopiaBinding;
import com.hometopia.scheduledservice.util.RepairMaintenanceVendorCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlRepairMaintenanceVendorJob {

    private final StreamBridge streamBridge;

    @Scheduled(cron = "${scheduling.computer-maintenance-vendor}")
    @Async
    public void crawlComputerRepairMaintenanceVendor() throws InterruptedException {
        List<Vendor> vendors = RepairMaintenanceVendorCrawler.getListVendors("Sửa máy tính TP HCM");
        streamBridge.send(HometopiaBinding.VENDOR, MessageBuilder.withPayload(vendors).build());
    }
}
