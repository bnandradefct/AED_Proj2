package system;

import java.io.Serializable;

/**
 * Representa um instante de tempo no formato 24h (HH:mm).
 * @space O(1)
 */
public class Time implements Serializable, Comparable<Time> {
    private static final long serialVersionUID = 1L;
    private final int minutesSinceMidnight;

    private Time(int minutes) {
        this.minutesSinceMidnight = minutes;
    }

    /**
     * Valida e constrói um Time. Se o formato ou valores forem inválidos, devolve null.
     * @time O(1)
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

    public int getMinutes() {
        return minutesSinceMidnight;
    }

    public boolean isBefore(Time o) {
        return this.minutesSinceMidnight < o.minutesSinceMidnight;
    }

    public boolean isAfter(Time o) {
        return this.minutesSinceMidnight > o.minutesSinceMidnight;
    }

    @Override
    public int compareTo(Time o) {
        return Integer.compare(this.minutesSinceMidnight, o.minutesSinceMidnight);
    }

    @Override
    public String toString() {
        int h = minutesSinceMidnight / 60;
        int m = minutesSinceMidnight % 60;
        return String.format("%02d:%02d", h, m);
    }
}
