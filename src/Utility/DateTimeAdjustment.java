package Utility;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeAdjustment {
    public static ZonedDateTime changeToCurrentTimezone(Instant recordedInstant) {
        ZoneId userZone = ZoneId.systemDefault();
        ZonedDateTime timeZone = recordedInstant.atZone(userZone);
        return timeZone;
    }

    public static String formatTime(ZonedDateTime zonedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String formattedString = zonedTime.format(formatter);
        return formattedString;
    }
}
