package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateUtil {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Get the current date in string with the format yyyy-MM-dd HH:mm
     * @return
     */
    public static String getCurrentDate() {
        LocalDate now = LocalDate.now();
        return now.format(formatter);
    }

    /**
     * Get the current date minus one month with the format yyyy-MM-dd HH:mm
     * @return
     */
    public static String getCurrentDateMinusOneMonth() {
        LocalDate now = LocalDate.now();
        LocalDate history = now.minusMonths(1);
        return history.format(formatter);
    }

    /**
     * Get the current date minus X months with the format yyyy-MM-dd HH:mm
     * @param months amount of months to go in the past
     * @return
     */
    public static String getCurrentDateMinusXMonths(long months) {
        LocalDate now = LocalDate.now();
        LocalDate history = now.minusMonths(months);
        return history.format(formatter);
    }
}
