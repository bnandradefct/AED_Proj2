import dataStructures.List;
import java.io.Serializable;

public interface Station extends Serializable {
    String getName();
    int getOrderInLine();
    List<Journey> getDepartures();
    void addDeparture(Journey journey);
    void removeDeparture(Journey journey);
}

