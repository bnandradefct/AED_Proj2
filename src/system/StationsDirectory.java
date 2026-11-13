package system;

import dataStructures.List;
import java.io.Serializable;

/**
 * StationsDirectory interface defining the operations of the transportation network.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 */
public interface StationsDirectory extends Serializable {

    /**
     * Returns the total number of stations loaded in the line.
     *
     * @return the station count
     */
    int getStationCount();

    /**
     * Initializes and adds a new station to the end of the line.
     *
     * @param stationName the name of the station
     */
    void initStation(String stationName);

    /**
     * Checks whether a station with the given name exists in the line.
     *
     * @param name the name of the station
     * @return true if the station exists, false otherwise
     */
    boolean hasStation(String name);

    /**
     * Checks if the origin station precedes the destination station along the line.
     *
     * @param originName the name of the origin station
     * @param destName the name of the destination station
     * @return true if both stations exist and origin comes before destination, false otherwise
     */
    boolean isValidOrder(String originName, String destName);

    /**
     * Checks whether a journey with the given identifier already exists.
     *
     * @param journeyId the journey identifier
     * @return true if the journey exists, false otherwise
     */
    boolean hasJourney(String journeyId);

    /**
     * Adds a new journey with its sequence of stops to the transportation network.
     *
     * @param id the unique journey identifier
     * @param stops flattened list of stop entries (station name, arrival time, departure time)
     * @return true if the journey was added, false if a journey with the same ID already exists
     */
    boolean addJourney(String id, List<String> stops);

    /**
     * Removes an existing journey from the transportation network.
     *
     * @param journeyId the identifier of the journey to remove
     * @return true if the journey was found and removed, false otherwise
     */
    boolean removeJourney(String journeyId);

    /**
     * Finds the identifier of the earliest direct journey from origin to destination
     * at or after the passenger's arrival time.
     *
     * @param originName the origin station name
     * @param destName the destination station name
     * @param passengerTime the passenger's arrival time at the origin station
     * @return the identifier of the best matching journey, or null if none is available
     */
    String findJourney(String originName, String destName, Time passengerTime);

    /**
     * Finds the identifier of the direct journey with the minimum number of stops
     * within a given departure time interval.
     *
     * @param originName the origin station name
     * @param destName the destination station name
     * @param minTime the minimum departure time
     * @param maxTime the maximum departure time
     * @return the identifier of the best matching journey, or null if none is available
     */
    String findJourneyMinStops(String originName, String destName, Time minTime, Time maxTime);

    /**
     * Returns the formatted departure or arrival time of a journey at a given station.
     *
     * @param id the journey identifier
     * @param name the station name
     * @param departure true to retrieve the departure time, false for the arrival time
     * @return the formatted HH:mm time string
     */
    String getTime(String id, String name, boolean departure);

    /**
     * Counts the number of stops between an origin station and a destination station for a given journey.
     *
     * @param id the journey identifier
     * @param origin the origin station name
     * @param dest the destination station name
     * @return the number of stops between origin and destination
     */
    int countStopsBetween(String id, String origin, String dest);

    /**
     * Returns the station name with its original line capitalization.
     *
     * @param name the station name to look up
     * @return the original station name, or null if not found
     */
    String getStationName(String name);
}
