package com.hometopia.vendorservice.dto.response;

public record GetListVendorResponse(
        String link,
        String name,
        String address,
        String website,
        String phoneNumber
) {}
