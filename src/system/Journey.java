package system;

import dataStructures.List;
import java.io.Serializable;

public interface Journey extends Serializable {
    String getId();
    int getInsertionOrder();
    List<Stop> getStops();
    boolean stopsAt(Station station);
    Stop getStop(Station station);
    int countStopsBetween(Station origin, Station destination);
}
