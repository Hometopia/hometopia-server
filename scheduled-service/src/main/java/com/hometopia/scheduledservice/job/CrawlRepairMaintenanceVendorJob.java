package com.hometopia.scheduledservice.job;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.message.Vendor;
import com.hometopia.proto.district.DistrictGrpcServiceGrpc;
import com.hometopia.proto.province.ProvinceGrpcServiceGrpc;
import com.hometopia.proto.ward.WardGrpcServiceGrpc;
import com.hometopia.scheduledservice.util.HometopiaBinding;
import com.hometopia.scheduledservice.util.RepairMaintenanceVendorCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
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

    @GrpcClient("core-service")
    private ProvinceGrpcServiceGrpc.ProvinceGrpcServiceBlockingStub provinceGrpcServiceBlockingStub;
    @GrpcClient("core-service")
    private DistrictGrpcServiceGrpc.DistrictGrpcServiceBlockingStub districtGrpcServiceBlockingStub;
    @GrpcClient("core-service")
    private WardGrpcServiceGrpc.WardGrpcServiceBlockingStub wardGrpcServiceBlockingStub;

    @Scheduled(cron = "${scheduling.computer-maintenance-vendor}")
    @Async
    public void crawlComputerRepairMaintenanceVendor() throws InterruptedException {
        List<Vendor> vendors = RepairMaintenanceVendorCrawler.getListVendors("Sửa máy tính Bình Thạnh",
                AssetCategory.LAPTOP, provinceGrpcServiceBlockingStub, districtGrpcServiceBlockingStub, wardGrpcServiceBlockingStub);
        streamBridge.send(HometopiaBinding.VENDOR, MessageBuilder.withPayload(vendors).build());
    }

    @Scheduled(cron = "${scheduling.mobile-phone-maintenance-vendor}")
    @Async
    public void crawlMobilePhoneRepairMaintenanceVendor() throws InterruptedException {
        List<Vendor> vendors = RepairMaintenanceVendorCrawler.getListVendors("Sửa điện thoại Thủ Đức",
                AssetCategory.MOBILE_PHONE, provinceGrpcServiceBlockingStub, districtGrpcServiceBlockingStub, wardGrpcServiceBlockingStub);
        streamBridge.send(HometopiaBinding.VENDOR, MessageBuilder.withPayload(vendors).build());
    }
}
