package com.hometopia.commons.utils;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class AppConstants {
    public static final String[] PERMITTED_HOSTS = {
            "http://localhost:3000"
    };
    public static final String DELIMITER_COMMA = ",";
    public static final String EMPTY_STRING = "";

    public static final Pattern GEO_LOCATION_PATTERN = Pattern.compile("@(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final String DEV_PROFILE = "dev";
    public static final String PROD_PROFILE = "prod";

    public static final String SCHEDULE_REMINDER_NOTIFICATION_TITLE = "Bạn có một lịch hẹn vào {0}.";
    public static final String SCHEDULE_REMINDER_NOTIFICATION_MESSAGE = "Bạn có một lịch hẹn vào {0} với {1}. Ấn vào đây để đi tới chi tiết lịch hẹn.";

    public static final String MAINTENANCE_REMINDER_NOTIFICATION_TITLE = "{0} đã tới thời gian cần được bảo trì.";
    public static final String MAINTENANCE_REMINDER_NOTIFICATION_MESSAGE = "{0} đã tới thời gian cần được bảo trì. Hãy đặt lịch hẹn bảo trì sớm.";

    public static final String SUGGESTED_MAINTENANCE_SCHEDULE_TITLE = "Bảo trì định kỳ {0} cho {1}.";
}
