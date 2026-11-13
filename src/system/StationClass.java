package system;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

/**
 * Implementation of a railway line Station.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(n), where n is the number of journeys registered at this station.
 */
public class StationClass implements Station {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int orderInLine;
    private final List<Journey> departures;

    /**
     * Constructs a new StationClass with the given name and position in the line.
     *
     * @param name the name of the station
     * @param orderInLine the 0-based order of the station along the line
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public StationClass(String name, int orderInLine) {
        this.name = name;
        this.orderInLine = orderInLine;
        this.departures = new DoublyLinkedList<Journey>();
    }

    /**
     * Returns the original name of the station.
     *
     * @return the station name
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public String getName() { return name; }

    /**
     * Returns the sequential position of the station along the line.
     *
     * @return the 0-based index of the station in the line
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public int getOrderInLine() { return orderInLine; }

    /**
     * Returns the list of journeys that stop at or depart from this station.
     *
     * @return list of journeys serving this station
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public List<Journey> getDepartures() { return departures; }

    /**
     * Registers a journey as serving this station.
     *
     * @param journey the journey to add
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public void addDeparture(Journey journey) {
        departures.addLast(journey);
    }

    /**
     * Removes a journey from this station's departures list.
     *
     * @param journey the journey to remove
     *
     * Time complexity:
     * - Best case: O(1) when the journey is at the head of the departures list
     * - Worst case: O(n) when the journey is at the tail or absent, where n is the number of departures at this station
     */
    @Override
    public void removeDeparture(Journey journey) {
        Iterator<Journey> it = departures.iterator();
        int idx = 0;
        while (it.hasNext()) {
            if (it.next().getId().equalsIgnoreCase(journey.getId())) {
                departures.remove(idx);
                return;
            }
            idx++;
        }
    }
}
