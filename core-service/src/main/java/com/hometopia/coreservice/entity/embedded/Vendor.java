package com.hometopia.coreservice.entity.embedded;

public record Vendor(
        String link,
        String name,
        String address,
        String website,
        String phoneNumber
) {}
