package Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class contains date time conversion methods and time checking methods. This allows the code to be cleaner and more
 * concise.
 * @author Nicholas Staley
 */
public abstract class DateTimeAdjustment {
    /**
     * Holds a final list of hours used in scheduling appointments.
     */
    private static final List<String> hourList = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    /**
     * Holds a final list of minutes used in scheduling appointments.
     */
    private static final List<String> minutesList = Arrays.asList("00", "15", "30", "45");
    /**
     * Holds the observable list of hours to populate combo boxes with.
     */
    public static final ObservableList<String> hoursCombo = FXCollections.observableArrayList(hourList);
    /**
     * Holds the observable list of minutes to populate combo boxes with.
     */
    public static final ObservableList<String> minutesCombo = FXCollections.observableArrayList(minutesList);

    /**
     * This method converts an instant to the zoned date time of the person using the program.
     * @param recordedInstant The instant to be converted
     * @return Returns a zoned date time of the instant that was supplied.
     */
    public static ZonedDateTime changeToCurrentTimezone(Instant recordedInstant) {
        ZoneId userZone = ZoneId.systemDefault();
        return recordedInstant.atZone(userZone);
    }

    /**
     * This method takes a zoned date time and converts it to a formatted string for use in the program.
     * @param zonedTime The zoned date time supplied
     * @return Returns a string of a formatted date and time.
     */
    public static String formatTime(ZonedDateTime zonedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return zonedTime.format(formatter);
    }

    /**
     * This method converts a string to a zoned date time of the system that is running the program.
     * @param inputTime the string input time that is to be converted
     * @return Returns a zoned date time of the string that was supplied.
     */
    public static ZonedDateTime convertStringToZoned(String inputTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        LocalDateTime localDateTime = LocalDateTime.parse(inputTime, formatter);
        return localDateTime.atZone(ZoneId.systemDefault());
    }

    /**
     * This method takes a zoned date time and returns a string of the hour of the time provided.
     * @param timeInput The zoned date time to be converted
     * @return Returns a string of the hour of the supplied zoned date time.
     */
    public static String getHour(ZonedDateTime timeInput) {
        int hour = timeInput.getHour();
        if (hour > 12) {
            hour -= 12;
        }
        return switch (hour) {
            case 1 -> "01";
            case 2 -> "02";
            case 3 -> "03";
            case 4 -> "04";
            case 5 -> "05";
            case 6 -> "06";
            case 7 -> "07";
            case 8 -> "08";
            case 9 -> "09";
            case 10 -> "10";
            case 11 -> "11";
            case 12 -> "12";
            default -> "error";
        };
    }

    /**
     * This method takes a zoned date time and returns a string of the minutes in the zoned date time.
     * @param timeInput The zoned date time to be converted
     * @return Returns a string of the minutes of the supplied zoned date time.
     */
    public static String getMinute(ZonedDateTime timeInput) {
        int minute = timeInput.getMinute();
        return switch (minute) {
            case 0 -> "00";
            case 15 -> "15";
            case 30 -> "30";
            case 45 -> "45";
            default -> "error";
        };
    }

    /**
     * This method checks if the zoned date time is in the am.
     * @param timeInput The zoned date time to be checked
     * @return Returns a true boolean if the supplied time is in the am or false if it is pm.
     */
    public static boolean amCheck(ZonedDateTime timeInput) {
        return timeInput.getHour() < 12;
    }

    /**
     * This method checks to see if the supplied appointment start and end times falls between the business hours of 08:00 and 22:00 est.
     * @param start The start time of the appointment
     * @param end The end time of the appointment
     * @return Returns an InputErrorCheck with a false boolean if the appointment was between business hour or a true boolean and error
     * message if the appointment falls outside business hours.
     */
    public static InputErrorCheck checkBusinessHours(ZonedDateTime start, ZonedDateTime end) {
        InputErrorCheck errorCheck = new InputErrorCheck();

        if (start.withZoneSameInstant(ZoneId.of("America/New_York")).getHour() < 8 || (start.withZoneSameInstant(ZoneId.of("America/New_York")).getHour() > 21) && (start.withZoneSameInstant(ZoneId.of("America/New_York")).getMinute() < 45)) {
            errorCheck.setWasError(true);
            errorCheck.concatErrorMessage("Start time must be between 08:00 and 21:45 EST.\n");
        }
        if (((end.withZoneSameInstant(ZoneId.of("America/New_York")).getHour() < 9) && (end.withZoneSameInstant(ZoneId.of("America/New_York")).getMinute() < 15)) || ((end.withZoneSameInstant(ZoneId.of("America/New_York")).getHour() > 21) && (end.withZoneSameInstant(ZoneId.of("America/New_York")).getMinute() > 0))) {
            errorCheck.setWasError(true);
            errorCheck.concatErrorMessage("End time must be between 08:15 and 22:00 EST.\n");
        }
        if ((start.withZoneSameInstant(ZoneId.of("America/New_York")).getDayOfMonth() - end.withZoneSameInstant(ZoneId.of("America/New_York")).getDayOfMonth()) != 0) {
            errorCheck.setWasError(true);
            errorCheck.concatErrorMessage("Appointments can not go past 22:00 EST to the next day.");
        }
        return errorCheck;
    }


}
