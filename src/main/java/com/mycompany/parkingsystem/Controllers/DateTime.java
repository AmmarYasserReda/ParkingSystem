/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProjectManagementSystemProject.Controller;

import java.util.Date;

/**
 *
 * @author ahmed maher
 */
public class DateTime {

    public DateTime() {
    }

    /**
     *
     * @return time in seconds
     */
    public static long nowTime() {
        long elapsed = (long) System.currentTimeMillis();
        int seconds = (int) (elapsed / 1000) % 60;
        int minutes = (int) (elapsed / (1000 * 60)) % 60;
        int hours = (int) (2 + (elapsed / (1000 * 3600))) % 24;

        return (long) (hours * 3600 + minutes * 60 + seconds);
    }

    /**
     *
     * @return example "15 jun 2023"
     */
    public static String nowDate() {
        Date date = new Date();
        String[] dateParts = date.toString().split(" ");

        return dateParts[2] + " " + dateParts[1] + " " + dateParts[5];
    }

    /**
     *
     * @return Months strings
     */
    public static String[] getMonths() {
        return new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }

    /**
     *
     * @param startTimeMS
     * @param endTimeMS
     * @return Difference between endTimeMS and startTimeMS
     */
    public static long calcDuration(double startTimeMS, double endTimeMS) {
        double totalTimeInSeconds = endTimeMS - startTimeMS;
        int seconds = (int) (totalTimeInSeconds % 60);
        int minutes = (int) ((totalTimeInSeconds / 60) % 60);
        int hours = (int) ((totalTimeInSeconds / 3600) % 24);
        return (long) (hours * 3600 + minutes * 60 + seconds);
    }

    /**
     *
     * @param firstDate Format "15 jun 2023"
     * @param secondDate Format "15 jun 2023"
     * @return Returns true if firstDate > secondDate, false Otherwise
     */
    public static boolean dateCMP(String firstDate, String secondDate) {
        String[] d1 = firstDate.split(" ");
        String[] d2 = secondDate.split(" ");
        String[] months = DateTime.getMonths();

        if (DateTime.toLong(d1[2]) == DateTime.toLong(d2[2])) {
            if (ArrayString.indexOF(months, d1[1]) == ArrayString.indexOF(months, d2[1])) {
                if (DateTime.toLong(d1[0]) == DateTime.toLong(d2[0])) {
                    return false;
                } else {
                    // Day Comparison
                    return DateTime.toLong(d1[0]) > DateTime.toLong(d2[0]);
                }
            } else {
                // Month Comparison
                return ArrayString.indexOF(months, d1[1]) > ArrayString.indexOF(months, d2[1]);
            }
        } else {
            // Year Comparison
            return DateTime.toLong(d1[2]) > DateTime.toLong(d2[2]);
        }
    }

    /**
     *
     * @param longValue
     * @return long representation of a string
     */
    public static long toLong(String longValue) {
        return Long.parseLong(longValue);
    }

    /**
     *
     * @param timeInSeconds
     * @return time representation in string HH:MM:SS
     */
    public static String toString(long timeInSeconds) {
        int seconds = (int) (timeInSeconds % 60);
        int minutes = (int) ((timeInSeconds / 60) % 60);
        int hours = (int) ((timeInSeconds / 3600) % 24);
        return hours + ":" + minutes + ":" + seconds;
    }
}
