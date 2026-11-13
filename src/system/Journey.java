package system;

import dataStructures.List;
import java.io.Serializable;

/**
 * Journey interface representing a scheduled vehicle run along the railway line.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 */
public interface Journey extends Serializable {

    /**
     * Returns the unique identifier of the journey.
     *
     * @return the journey identifier
     */
    String getId();

    /**
     * Returns the chronological insertion order of the journey in the system.
     *
     * @return the 0-based insertion order index
     */
    int getInsertionOrder();

    /**
     * Returns the ordered list of stops made by this journey.
     *
     * @return list of stops
     */
    List<Stop> getStops();

    /**
     * Checks whether this journey stops at the specified station.
     *
     * @param station the station to check
     * @return true if the journey stops at the station, false otherwise
     */
    boolean stopsAt(Station station);

    /**
     * Returns the stop information for the specified station.
     *
     * @param station the station to retrieve the stop for
     * @return the Stop at the station, or null if the journey does not stop there
     */
    Stop getStop(Station station);

    /**
     * Counts the number of stops between an origin station and a destination station.
     *
     * @param origin the origin station
     * @param destination the destination station
     * @return the number of stops between origin and destination, or -1 if either stop is missing
     */
    int countStopsBetween(Station origin, Station destination);
}
