package com.hometopia.commons.utils;

import java.util.regex.Pattern;

public class AppConstants {
    public static final String[] PERMITTED_HOSTS = {
            "http://localhost:3000"
    };
    public static final String DELIMITER_COMMA = ",";
    public static final String EMPTY_STRING = "";
    public static final Pattern GEO_LOCATION_PATTERN = Pattern.compile("@(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)");
    public static final String DEV_PROFILE = "dev";
    public static final String PROD_PROFILE = "prod";
}
