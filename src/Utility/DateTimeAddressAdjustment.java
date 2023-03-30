package Utility;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeAddressAdjustment {
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

    public static String formatAddress(String originalAddress, int countryCode) {
        String newAddress = "";
        if(countryCode == 1) {

        }
        if(countryCode == 2) {

        }
        if(countryCode == 3) {

        }
        return newAddress;
    }
}
