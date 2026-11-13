package system;

import dataStructures.List;
import java.io.Serializable;

/**
 * Station interface representing a station on the railway line.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 */
public interface Station extends Serializable {

    /**
     * Returns the original name of the station.
     *
     * @return the station name
     */
    String getName();

    /**
     * Returns the sequential position of the station along the line.
     *
     * @return the 0-based index of the station in the line
     */
    int getOrderInLine();

    /**
     * Returns the list of journeys that stop at or depart from this station.
     *
     * @return list of journeys serving this station
     */
    List<Journey> getDepartures();

    /**
     * Registers a journey as serving this station.
     *
     * @param journey the journey to add
     */
    void addDeparture(Journey journey);

    /**
     * Removes a journey from this station's departures list.
     *
     * @param journey the journey to remove
     */
    void removeDeparture(Journey journey);
}
