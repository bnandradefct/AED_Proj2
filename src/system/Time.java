package system;

import java.io.Serializable;

/**
 * Represents a 24-hour time instant in HH:mm format.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(1)
 */
public class Time implements Serializable, Comparable<Time> {
    private static final long serialVersionUID = 1L;
    private final int minutesSinceMidnight;

    /**
     * Constructs a Time instance from minutes elapsed since midnight.
     *
     * @param minutes total minutes since 00:00
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    private Time(int minutes) {
        this.minutesSinceMidnight = minutes;
    }

    /**
     * Validates and parses a string in strict HH:mm 24-hour format into a Time object.
     *
     * @param s the string to parse
     * @return a valid Time instance, or null if the format or range is invalid
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public static Time parse(String s) {
        if (s == null || s.length() != 5 || s.charAt(2) != ':') {
            return null;
        }
        for (int i = 0; i < 5; i++) {
            if (i == 2) continue;
            if (!Character.isDigit(s.charAt(i))) {
                return null;
            }
        }
        int hours = Integer.parseInt(s.substring(0, 2));
        int minutes = Integer.parseInt(s.substring(3, 5));

        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            return null;
        }
        return new Time(hours * 60 + minutes);
    }

    /**
     * Returns the total number of minutes elapsed since midnight.
     *
     * @return minutes since midnight
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public int getMinutes() {
        return minutesSinceMidnight;
    }

    /**
     * Checks if this time is strictly earlier than the given time.
     *
     * @param o the other Time to compare with
     * @return true if this time is before o, false otherwise
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public boolean isBefore(Time o) {
        return this.minutesSinceMidnight < o.minutesSinceMidnight;
    }

    /**
     * Checks if this time is strictly later than the given time.
     *
     * @param o the other Time to compare with
     * @return true if this time is after o, false otherwise
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public boolean isAfter(Time o) {
        return this.minutesSinceMidnight > o.minutesSinceMidnight;
    }

    /**
     * Compares this time chronologically with another Time instance.
     *
     * @param o the other Time to compare with
     * @return a negative integer, zero, or a positive integer as this time is earlier than, equal to, or later than o
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public int compareTo(Time o) {
        return Integer.compare(this.minutesSinceMidnight, o.minutesSinceMidnight);
    }

    /**
     * Formats this time as a two-digit HH:mm string.
     *
     * @return the formatted time string
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public String toString() {
        int h = minutesSinceMidnight / 60;
        int m = minutesSinceMidnight % 60;
        return String.format("%02d:%02d", h, m);
    }
}
