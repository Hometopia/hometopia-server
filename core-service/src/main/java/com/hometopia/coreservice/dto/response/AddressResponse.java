package com.hometopia.coreservice.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
        String line,
        ProvinceResponse province,
        DistrictResponse district,
        WardResponse ward
) {}
