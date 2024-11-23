package com.hometopia.coreservice.dto.response;

import lombok.Builder;

@Builder
public record DistrictResponse(
        Integer code,
        String name
) {}
