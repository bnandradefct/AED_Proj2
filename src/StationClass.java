import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

/**
 * Representa uma estação da linha ferroviária.
 * @space O(D) onde D é o número de viagens registadas que partem/passam nesta estação.
 */
public class StationClass implements Station {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int orderInLine;
    private final List<Journey> departures;

    public StationClass(String name, int orderInLine) {
        this.name = name;
        this.orderInLine = orderInLine;
        this.departures = new DoublyLinkedList<Journey>();
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getOrderInLine() { return orderInLine; }

    @Override
    public List<Journey> getDepartures() { return departures; }

    @Override
    public void addDeparture(Journey journey) {
        departures.addLast(journey);
    }

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
