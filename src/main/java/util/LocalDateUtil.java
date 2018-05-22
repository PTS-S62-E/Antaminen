package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Get the current date in string with the format yyyy-MM-dd HH:mm
     * @return
     */
    public static String getCurrentDate() {
        LocalDate now = LocalDate.now();
        return now.format(formatter) + " 23:59";
    }

    /**
     * Get the current date minus one month with the format yyyy-MM-dd HH:mm
     * @return
     */
    public static String getCurrentDateMinusOneMonth() {
        LocalDate now = LocalDate.now();
        LocalDate history = now.minusMonths(1);
        return history.format(formatter) + " 23:59";
    }

    /**
     * Get the current date minus X months with the format yyyy-MM-dd HH:mm
     * @param months amount of months to go in the past
     * @return
     */
    public static String getCurrentDateMinusXMonths(long months) {
        LocalDate now = LocalDate.now();
        LocalDate history = now.minusMonths(months);
        return history.format(formatter) + " 23:59";
    }

    /**
     * Check whether a date String is correctly formatted so it can be parsed to a LocalDate object
     *
     * @param date The string that you want to check
     * @return returns true when the string can be parsed to a LocalDate object, false if an exception occurred while parsing
     */
    public static boolean isStringDateValid(String date) {
        try {
            LocalDate localDate = (LocalDate) formatter.parse(date);
            return true;
        } catch (DateTimeParseException dtpe) {
            // The provided date string is not correctly formatted.
            // Do nothing here
        }

        return false;
    }
}
