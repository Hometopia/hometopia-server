package com.hometopia.vendorservice.config;

import com.hometopia.commons.message.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class StreamConfig {

    @Bean
    public Consumer<Message<List<Vendor>>> handleSaveListVendor() {
        return message -> {
            List<Vendor> vendors = message.getPayload();
            System.out.println(vendors.size());
        };
    }
}
