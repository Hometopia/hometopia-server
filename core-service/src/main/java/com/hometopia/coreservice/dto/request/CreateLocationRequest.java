package com.hometopia.coreservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateLocationRequest(
        @NotBlank
        String name
) {}
