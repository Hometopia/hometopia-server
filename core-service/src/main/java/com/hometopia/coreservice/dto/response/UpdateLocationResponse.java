package com.hometopia.coreservice.dto.response;

import com.hometopia.coreservice.entity.embedded.File;

import java.util.ArrayList;

public record UpdateLocationResponse(
        String id,
        String name,
        ArrayList<File> images
) {}
