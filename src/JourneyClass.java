import dataStructures.DoublyLinkedList;
import dataStructures.List;
import dataStructures.Map;
import dataStructures.SepChainHashTable;

/**
 * Representa uma viagem ao longo da linha.
 * @space O(S) onde S é o número de paragens da viagem.
 */
public class JourneyClass implements Journey {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final int insertionOrder;
    private final List<Stop> stops;
    private final Map<String, Stop> stopsMap;

    public JourneyClass(String id, int insertionOrder) {
        this.id = id;
        this.insertionOrder = insertionOrder;
        this.stops = new DoublyLinkedList<Stop>();
        this.stopsMap = new SepChainHashTable<String, Stop>();
    }

    public void addStop(Stop stop) {
        stops.addLast(stop);
        stopsMap.put(stop.getStation().getName().toLowerCase(), stop);
    }

    @Override
    public String getId() { 
      return id; 
    }

    @Override
    public int getInsertionOrder() { 
      return insertionOrder; 
    }

    @Override
    public List<Stop> getStops() { 
      return stops; 
    }

    @Override
    public boolean stopsAt(Station station) {
        return stopsMap.get(station.getName().toLowerCase()) != null;
    }

    @Override
    public Stop getStop(Station station) {
        return stopsMap.get(station.getName().toLowerCase());
    }

    @Override
    public int countStopsBetween(Station origin, Station destination) {
        Stop sOrig = getStop(origin);
        Stop sDest = getStop(destination);
        if (sOrig == null || sDest == null) return -1;
        return sDest.getStopIndex() - sOrig.getStopIndex();
    }
}
