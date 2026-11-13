package system;

import dataStructures.DoublyLinkedList;
import dataStructures.List;
import dataStructures.Map;
import dataStructures.SepChainHashTable;

/**
 * Implementation of a Journey along the railway line.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(n), where n is the number of stops in the journey.
 */
public class JourneyClass implements Journey {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final int insertionOrder;
    private final List<Stop> stops;
    private final Map<String, Stop> stopsMap;

    /**
     * Constructs a new JourneyClass with the given identifier and insertion order.
     *
     * @param id the journey identifier
     * @param insertionOrder the chronological insertion index
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public JourneyClass(String id, int insertionOrder) {
        this.id = id;
        this.insertionOrder = insertionOrder;
        this.stops = new DoublyLinkedList<Stop>();
        this.stopsMap = new SepChainHashTable<String, Stop>();
    }

    /**
     * Appends a stop to the end of this journey.
     *
     * @param stop the stop to add
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) when rehashing occurs, where n is the number of stops in the journey
     */
    public void addStop(Stop stop) {
        stops.addLast(stop);
        stopsMap.put(stop.getStation().getName().toLowerCase(), stop);
    }

    /**
     * Returns the unique identifier of the journey.
     *
     * @return the journey identifier
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public String getId() { 
      return id; 
    }

    /**
     * Returns the chronological insertion order of the journey in the system.
     *
     * @return the 0-based insertion order index
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public int getInsertionOrder() { 
      return insertionOrder; 
    }

    /**
     * Returns the ordered list of stops made by this journey.
     *
     * @return list of stops
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public List<Stop> getStops() { 
      return stops; 
    }

    /**
     * Checks whether this journey stops at the specified station.
     *
     * @param station the station to check
     * @return true if the journey stops at the station, false otherwise
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stops in the journey
     */
    @Override
    public boolean stopsAt(Station station) {
        return stopsMap.get(station.getName().toLowerCase()) != null;
    }

    /**
     * Returns the stop information for the specified station.
     *
     * @param station the station to retrieve the stop for
     * @return the Stop at the station, or null if the journey does not stop there
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stops in the journey
     */
    @Override
    public Stop getStop(Station station) {
        return stopsMap.get(station.getName().toLowerCase());
    }

    /**
     * Counts the number of stops between an origin station and a destination station.
     *
     * @param origin the origin station
     * @param destination the destination station
     * @return the number of stops between origin and destination, or -1 if either stop is missing
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(n) in case of hash collisions, where n is the number of stops in the journey
     */
    @Override
    public int countStopsBetween(Station origin, Station destination) {
        Stop sOrig = getStop(origin);
        Stop sDest = getStop(destination);
        if (sOrig == null || sDest == null) return -1;
        return sDest.getStopIndex() - sOrig.getStopIndex();
    }
}
