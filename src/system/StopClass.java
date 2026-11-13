package system;

/**
 * Implementation of a Stop within a journey.
 *
 * @author Bernardo Andrade 68024; Rodrigo Pinheiro 69067
 * @version 1.0
 *
 * Space complexity: O(1)
 */
public class StopClass implements Stop {
    private static final long serialVersionUID = 1L;
    private final Station station;
    private final Time arrivalTime;
    private final Time departureTime;
    private final int stopIndex;

    /**
     * Constructs a new StopClass.
     *
     * @param station the station of the stop
     * @param arrivalTime the arrival time (null for the first stop)
     * @param departureTime the departure time (null for the last stop)
     * @param stopIndex the 0-based index of the stop in the journey
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    public StopClass(Station station, Time arrivalTime, Time departureTime, int stopIndex) {
        this.station = station;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopIndex = stopIndex;
    }

    /**
     * Returns the station associated with this stop.
     *
     * @return the Station
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public Station getStation() { 
      return station; 
    }

    /**
     * Returns the arrival time at this station, or null if this is the first stop.
     *
     * @return the arrival Time, or null
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public Time getArrivalTime() { 
      return arrivalTime; 
    }

    /**
     * Returns the departure time from this station, or null if this is the last stop.
     *
     * @return the departure Time, or null
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public Time getDepartureTime() { 
      return departureTime; 
    }

    /**
     * Returns the 0-based sequential index of this stop within the journey.
     *
     * @return the stop index
     *
     * Time complexity:
     * - Best case: O(1)
     * - Worst case: O(1)
     */
    @Override
    public int getStopIndex() {
       return stopIndex; 
      }
}
