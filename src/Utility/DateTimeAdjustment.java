package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateTimeAdjustment {
    private static final List<String> hourList = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    private static final List<String> minutesList = Arrays.asList("00", "15", "30", "45");
    public static final ObservableList<String> hoursCombo = FXCollections.observableArrayList(hourList);
    public static final ObservableList<String> minutesCombo = FXCollections.observableArrayList(minutesList);

    public static ZonedDateTime changeToCurrentTimezone(Instant recordedInstant) {
        ZoneId userZone = ZoneId.systemDefault();
        ZonedDateTime timeZone = recordedInstant.atZone(userZone);
        return timeZone;
    }

    public static Instant changeToUTC(ZonedDateTime timeToChange) {
        Instant instant = timeToChange.toInstant();
        System.out.println(timeToChange);
        System.out.println(instant);

        return instant;
    }

    public static String formatTime(ZonedDateTime zonedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String formattedString = zonedTime.format(formatter);
        return formattedString;
    }

    public static ZonedDateTime convertStringToZoned(String inputTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        LocalDateTime localDateTime = LocalDateTime.parse(inputTime, formatter);
        ZonedDateTime convertedTime = localDateTime.atZone(ZoneId.systemDefault());
        return convertedTime;
    }


}
