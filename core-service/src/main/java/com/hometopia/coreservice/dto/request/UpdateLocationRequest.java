package com.hometopia.coreservice.dto.request;

import com.hometopia.coreservice.entity.embedded.File;

import java.util.ArrayList;

public record UpdateLocationRequest(
        String name,
        ArrayList<File> images
) {}
