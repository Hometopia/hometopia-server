package com.hometopia.coreservice.dto.response;

import java.time.Instant;

public record GetListAssetLifeCycleResponse(
        String id,
        Instant timestamp,
        String description
) {}
