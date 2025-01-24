package com.hometopia.coreservice.dto.request;

import com.hometopia.coreservice.entity.embedded.File;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;

public record CreateLocationRequest(
        @NotBlank
        String name,
        ArrayList<File> images
) {}
