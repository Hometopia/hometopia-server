package com.hometopia.vendorservice.config;

import com.hometopia.commons.message.Vendor;
import com.hometopia.vendorservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class StreamConfig {

    private final VendorService vendorService;

    @Bean
    public Consumer<Message<List<Vendor>>> handleSaveListVendor() {
        return message -> vendorService.saveListVendors(message.getPayload());
    }
}
