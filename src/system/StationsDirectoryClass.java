package system;

import dataStructures.*;

import java.io.Serializable;

/**
 * Implementation of the transportation network directory managing stations and journeys.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(s + j * k), where s is the number of stations on the line,
 * j is the number of registered journeys, and k is the average number of stops per journey.
 */
public class StationsDirectoryClass implements StationsDirectory, Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Station> lineStations;
    private final Map<String, Station> stationsMap;
    private final Map<String, Journey> journeysMap;
    private int insertionCounter;

    /**
     * Constructs an empty StationsDirectoryClass.
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public StationsDirectoryClass() {
        this.lineStations = new DoublyLinkedList<Station>();
        this.stationsMap = new SepChainHashTable<String, Station>();
        this.journeysMap = new SepChainHashTable<String, Journey>();
        this.insertionCounter = 0;
    }

    /**
     * Returns the total number of stations loaded in the line.
     *
     * @return the station count
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public int getStationCount() {
        return lineStations.size();
    }

    /**
     * Initializes and adds a new station to the end of the line.
     *
     * @param stationName the name of the station
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) when rehashing occurs, where n is the number of stations
     */
    @Override
    public void initStation(String stationName) {
        Station st = new StationClass(stationName, lineStations.size());
        lineStations.addLast(st);
        stationsMap.put(stationName.toLowerCase(), st);
    }

    /**
     * Checks whether a station with the given name exists in the line.
     *
     * @param name the name of the station
     * @return true if the station exists, false otherwise
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stations
     */
    @Override
    public boolean hasStation(String name) {
        return getStation(name) != null;
    }

    /**
     * Checks if the origin station precedes the destination station along the line.
     *
     * @param originName the name of the origin station
     * @param destName the name of the destination station
     * @return true if both stations exist and origin comes before destination, false otherwise
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stations
     */
    @Override
    public boolean isValidOrder(String originName, String destName) {
        Station origin = getStation(originName);
        Station dest = getStation(destName);
        if (origin == null || dest == null) {
            return false;
        }
        return origin.getOrderInLine() < dest.getOrderInLine();
    }

    /**
     * Checks whether a journey with the given identifier already exists.
     *
     * @param journeyId the journey identifier
     * @return true if the journey exists, false otherwise
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of registered journeys
     */
    @Override
    public boolean hasJourney(String journeyId) {
        if (journeyId == null) return false;
        return journeysMap.get(journeyId.toLowerCase()) != null;
    }

    /**
     * Adds a new journey with its sequence of stops to the transportation network.
     *
     * @param id the unique journey identifier
     * @param stopsData flattened list of stop entries (station name, arrival time, departure time)
     * @return true if the journey was added, false if a journey with the same ID already exists
     *
     * Time complexity:
     * - Best case: O(1) when the journey identifier already exists
     * - Worst case: O(n + m) when rehashing occurs, where n is the number of stops in the journey and m is the total number of registered journeys
     */
    @Override
    public boolean addJourney(String id, List<String> stopsData) {
        if (hasJourney(id)) {
            return false;
        }
        JourneyClass journey = new JourneyClass(id, insertionCounter++);
        Iterator<String> it = stopsData.iterator();
        int index = 0;
        while(it.hasNext()) {
            String stationName = it.next();
            String arr = it.next();
            String dep = it.next();
            Station st = getStation(stationName);
            Stop stop = new StopClass(st, Time.parse(arr), Time.parse(dep), index++);
            journey.addStop(stop);
            st.addDeparture(journey);
        }

        journeysMap.put(id.toLowerCase(), journey);
        return true;
    }

    /**
     * Removes an existing journey from the transportation network.
     *
     * @param journeyId the identifier of the journey to remove
     * @return true if the journey was found and removed, false otherwise
     *
     * Time complexity:
     * - Best case: O(1) when the journey does not exist
     * - Worst case: O(n * m + k), where n is the number of stops in the journey, m is the average departures per station, and k is the total number of registered journeys
     */
    @Override
    public boolean removeJourney(String journeyId) {
        Journey journey = journeysMap.get(journeyId.toLowerCase());
        if (journey == null) {
            return false;
        }

        journeysMap.remove(journeyId.toLowerCase());

        Iterator<Stop> it = journey.getStops().iterator();
        while (it.hasNext()) {
            Stop stop = it.next();
            stop.getStation().removeDeparture(journey);
        }
        return true;
    }

    /**
     * Finds the identifier of the earliest direct journey from origin to destination
     * at or after the passenger's arrival time.
     *
     * @param originName the origin station name
     * @param destName the destination station name
     * @param passengerTime the passenger's arrival time at the origin station
     * @return the identifier of the best matching journey, or null if none is available
     *
     * Time complexity:
     * - Best case: O(1) when either station does not exist
     * - Worst case: O(n), where n is the number of journeys departing from the origin station
     */
    @Override
    public String findJourney(String originName, String destName, Time passengerTime) {
        Station origin = getStation(originName);
        Station dest = getStation(destName);
        if (origin == null || dest == null) {
            return null;
        }

        Journey bestJourney = null;
        Time bestDepTime = null;

        Iterator<Journey> it = origin.getDepartures().iterator();
        while (it.hasNext()) {
            Journey j = it.next();
            Stop origStop = j.getStop(origin);
            Stop destStop = j.getStop(dest);

            if (origStop != null && destStop != null && origStop.getStopIndex() < destStop.getStopIndex()) {
                Time dep = origStop.getDepartureTime();
                if (dep != null && !dep.isBefore(passengerTime)) {
                    if (bestJourney == null || dep.isBefore(bestDepTime) ||
                       (dep.compareTo(bestDepTime) == 0 && j.getInsertionOrder() < bestJourney.getInsertionOrder())) {
                        bestJourney = j;
                        bestDepTime = dep;
                    }
                }
            }
        }
        if (bestJourney == null) {
            return null;
        }
        return bestJourney.getId();
    }

    /**
     * Finds the identifier of the direct journey with the minimum number of stops
     * within a given departure time interval.
     *
     * @param originName the origin station name
     * @param destName the destination station name
     * @param minTime the minimum departure time
     * @param maxTime the maximum departure time
     * @return the identifier of the best matching journey, or null if none is available
     *
     * Time complexity:
     * - Best case: O(1) when either station does not exist
     * - Worst case: O(n), where n is the number of journeys departing from the origin station
     */
    @Override
    public String findJourneyMinStops(String originName, String destName, Time minTime, Time maxTime) {
        Station origin = getStation(originName);
        Station dest = getStation(destName);
        if (origin == null || dest == null) {
            return null;
        }

        Journey bestJourney = null;
        int minStops = Integer.MAX_VALUE;
        Time bestArrTime = null;
        Time bestDepTime = null;

        Iterator<Journey> it = origin.getDepartures().iterator();
        while (it.hasNext()) {
            Journey j = it.next();
            if (j.stopsAt(dest)) {
                Stop origStop = j.getStop(origin);
                Stop destStop = j.getStop(dest);

                if (origStop.getStopIndex() < destStop.getStopIndex()) {
                    Time dep = origStop.getDepartureTime();
                    Time arr = destStop.getArrivalTime();

                    if (dep != null && !dep.isBefore(minTime) && !dep.isAfter(maxTime)) {
                        int stops = j.countStopsBetween(origin, dest);

                        if (bestJourney == null) {
                            bestJourney = j;
                            minStops = stops;
                            bestArrTime = arr;
                            bestDepTime = dep;
                        } else if (stops < minStops) {
                            bestJourney = j;
                            minStops = stops;
                            bestArrTime = arr;
                            bestDepTime = dep;
                        } else if (stops == minStops) {
                            if (arr.isBefore(bestArrTime)) {
                                bestJourney = j;
                                bestArrTime = arr;
                                bestDepTime = dep;
                            } else if (arr.compareTo(bestArrTime) == 0) {
                                if (dep.isBefore(bestDepTime)) {
                                    bestJourney = j;
                                    bestDepTime = dep;
                                } else if (dep.compareTo(bestDepTime) == 0 &&
                                           j.getInsertionOrder() < bestJourney.getInsertionOrder()) {
                                    bestJourney = j;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (bestJourney == null) {
            return null;
        }
        return bestJourney.getId();
    }

    /**
     * Returns the formatted departure or arrival time of a journey at a given station.
     *
     * @param Id the journey identifier
     * @param name the station name
     * @param departure true to retrieve the departure time, false for the arrival time
     * @return the formatted HH:mm time string
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(s + j + k) in case of hash collisions, where s is the number of stations, j is the number of journeys, and k is the number of stops in the journey
     */
    @Override
    public String getTime (String Id, String name, boolean departure){
        Station station = getStation(name);
        Journey j = journeysMap.get(Id.toLowerCase());
        if(departure) {
             return j.getStop(station).getDepartureTime().toString();
        }
        return j.getStop(station).getArrivalTime().toString();
    }

    /**
     * Counts the number of stops between an origin station and a destination station for a given journey.
     *
     * @param id the journey identifier
     * @param origin the origin station name
     * @param dest the destination station name
     * @return the number of stops between origin and destination
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(s + j + k) in case of hash collisions, where s is the number of stations, j is the number of journeys, and k is the number of stops in the journey
     */
    @Override
    public int countStopsBetween(String id, String origin, String dest) {
        Journey j = journeysMap.get(id.toLowerCase());
        Station originStation = getStation(origin);
        Station destStation = getStation(dest);
        return j.countStopsBetween(originStation, destStation);
    }

    /**
     * Returns the station name with its original line capitalization.
     *
     * @param name the station name to look up
     * @return the original station name, or null if not found
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stations
     */
    @Override
    public String getStationName(String name) {
        Station st = getStation(name);
        if (st == null) {
            return null;
        }
        return st.getName();
    }

    /**
     * Retrieves a station by its name (case-insensitive).
     *
     * @param name the name of the station
     * @return the Station object, or null if not found
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stations
     */
    private Station getStation(String name) {
        if (name == null) return null;
        return stationsMap.get(name.toLowerCase());
    }

}
