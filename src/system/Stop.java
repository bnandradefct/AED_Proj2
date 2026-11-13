package system;

import java.io.Serializable;

/**
 * Stop interface representing a scheduled stop of a journey at a station.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 */
public interface Stop extends Serializable {

    /**
     * Returns the station associated with this stop.
     *
     * @return the Station
     */
    Station getStation();

    /**
     * Returns the arrival time at this station, or null if this is the first stop.
     *
     * @return the arrival Time, or null
     */
    Time getArrivalTime();

    /**
     * Returns the departure time from this station, or null if this is the last stop.
     *
     * @return the departure Time, or null
     */
    Time getDepartureTime();

    /**
     * Returns the 0-based sequential index of this stop within the journey.
     *
     * @return the stop index
     */
    int getStopIndex();
}
