import java.io.Serializable;

public interface Stop extends Serializable {
    Station getStation();
    Time getArrivalTime();
    Time getDepartureTime();
    int getStopIndex();
}
